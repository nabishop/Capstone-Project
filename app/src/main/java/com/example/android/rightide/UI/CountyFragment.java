package com.example.android.rightide.UI;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.rightide.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapters.CountyListBeachAdapter;
import Models.County;
import Utils.CountyLoader;
import Utils.CountyLocationLoader;

/* UNSPLASH API ANDROID LICENSE
MIT License

Copyright (c) 2017 Keenen Charles

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

public class CountyFragment extends Fragment {
    public static final String SAVED_BEACHES_STATE = "saved_beaches";
    private static final String SAVED_BEACHES_INSTANCE_KEY = "beaches_instance_key";

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location lastLocation;
    private String county;
    private static List<County> countyList;

    private static CountyListBeachAdapter adapter;
    ProgressBar progressBar;
    private TextView countyTextView;
    private ImageView beachPicture;
    Unsplash unsplash;

    public CountyFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (countyList != null)
            outState.putParcelableArrayList(SAVED_BEACHES_INSTANCE_KEY, new ArrayList<>(countyList));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MasterActivity.REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("CountyFragment", "Location Permission Granted");
                } else {
                    Log.d("CountyFragment", "Location Permission Denied");
                    Toast.makeText(getContext(), "Heck! We need this to load your county automatically!",
                            Toast.LENGTH_LONG).show();
                }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.county_fragment, container, false);

        countyTextView = root.findViewById(R.id.county_fragment_county_name);
        progressBar = root.findViewById(R.id.county_fragment_progress_bar);
        beachPicture = root.findViewById(R.id.county_fragment_beach_image_top);
        unsplash = new Unsplash(getResources().getString(R.string.unsplash_auth_key));

        if (savedInstanceState != null) {
            countyList = savedInstanceState.getParcelableArrayList(SAVED_BEACHES_INSTANCE_KEY);
            if (countyList != null)
                loadUI(countyList, root);
            else
                getDeviceCurrentLocation();
        } else
            getDeviceCurrentLocation();

        if (countyList != null)
            getActivity().setTitle("RighTide - " + countyList.get(0).getCountyName());
        else
            getActivity().setTitle("RighTide");

        return root;
    }

    private void loadUI(List<County> countyList, View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.county_fragment_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        String query = county + " " + countyList.get(0).getBeachesInCounty().get(0).getBeachName();
        unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                List<Photo> photos = results.getResults();
                Photo best = photos.get(0);
                Picasso.get().load(best.getUrls().getRegular()).into(beachPicture);
            }

            @Override
            public void onError(String error) {
                beachPicture.setImageDrawable(getResources().getDrawable(R.drawable.county_test));
                Toast.makeText(getContext(), "Heck had trouble finding a photo!", Toast.LENGTH_SHORT).show();
            }
        });

        countyTextView.setText(county);

        CountyListBeachAdapter countyListBeachAdapter = new CountyListBeachAdapter();
        countyListBeachAdapter.setBeachesAndContext(countyList, getContext(), getFragmentManager());

        recyclerView.setAdapter(countyListBeachAdapter);
        Log.d("CountyFragment", "County Retrieval Success!! Counties is " + countyList.toString());
    }

    private void getDeviceCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                    }, MasterActivity.REQUEST_LOCATION);
            Log.d("CountyFragment", "Location Permission Requested");
        } else {
            Log.d("CountyFragment", "Location Permission already Active");
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    progressBar.setVisibility(View.VISIBLE);

                    lastLocation = location;
                    Log.d("CountyFragment", "Latitude is " + lastLocation.getLatitude()
                            + " and longitude is " + lastLocation.getLongitude());
                    List<Double> coordinates = new ArrayList<>();
                    coordinates.add(lastLocation.getLatitude());
                    coordinates.add(lastLocation.getLongitude());

                    new CountyListASyncTask(getContext(), getView()).execute(coordinates);
                } else {
                    Log.e("CountyFragment", "onComplete Location Error");
                    Toast.makeText(getContext(), "Unable to get current location!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class CountyListASyncTask extends AsyncTask<List<Double>, Void, List<County>> {
        private Context context;
        private View view;

        private CountyListASyncTask(Context context, View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        protected List<County> doInBackground(List<Double>... lists) {
            if (lists == null || lists.length < 1 || lists[0] == null)
                return null;

            Log.d("ASyncTask", "lat is " + lists[0].get(0) + " long is " + lists[0].get(1));
            county = CountyLocationLoader.getCountyName(lists[0].get(0), lists[0].get(1));
            Log.d("ASyncTask", "County name is: " + county);
            return CountyLoader.getCountyList(county);
        }

        @Override
        protected void onPostExecute(List<County> counties) {
            progressBar.setVisibility(View.GONE);


            if (counties != null) {
                Collections.sort(counties);
                countyList = counties;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                RecyclerView recyclerView = view.findViewById(R.id.county_fragment_recycler_view);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);

                String query = county + " " + counties.get(0).getBeachesInCounty().get(0).getBeachName();
                unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
                    @Override
                    public void onComplete(SearchResults results) {
                        List<Photo> photos = results.getResults();
                        Photo best = photos.get(0);
                        Picasso.get().load(best.getUrls().getRegular()).into(beachPicture);
                    }

                    @Override
                    public void onError(String error) {
                        beachPicture.setImageDrawable(getResources().getDrawable(R.drawable.county_test));
                        Toast.makeText(getContext(), "Heck had trouble finding a photo!", Toast.LENGTH_SHORT).show();
                    }
                });

                countyTextView.setText(county);

                CountyListBeachAdapter countyListBeachAdapter = new CountyListBeachAdapter();
                countyListBeachAdapter.setBeachesAndContext(counties, getContext(), getFragmentManager());

                recyclerView.setAdapter(countyListBeachAdapter);
                Log.d("CountyFragment", "County Retrieval Success!! Counties is " + counties.toString());
            } else {
                Toast.makeText(getContext(), "Sorry! No beaches were found in your county!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
