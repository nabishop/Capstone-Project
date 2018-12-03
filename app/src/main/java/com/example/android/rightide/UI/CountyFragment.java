package com.example.android.rightide.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import Adapters.CountyListBeachAdapter;
import Models.County;
import Utils.CountyLoader;
import Utils.CountyLocationLoader;

public class CountyFragment extends Fragment {
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location lastLocation;
    private String county;
    private List<County> counties;

    private static CountyListBeachAdapter adapter;
    private TextView countyTextView;

    public CountyFragment() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MasterActivity.REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("CountyFragment", "Location Permission Granted");
                    getDeviceCurrentLocation();
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

        getDeviceCurrentLocation();

        return root;
    }

    private void getDeviceCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                    }, MasterActivity.REQUEST_LOCATION);
            Log.d("CountyFragment", "Location Permission Requested");
        } else
            Log.d("CountyFragment", "Location Permission already Active");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lastLocation = location;
                    Log.d("CountyFragment", "Latitude is " + lastLocation.getLatitude()
                            + " and longitude is " + lastLocation.getLongitude());
                    county = CountyLocationLoader.getCountyName(lastLocation.getLatitude(), lastLocation.getLongitude());
                    counties = CountyLoader.getCountyList(county);

                    if (counties != null)
                        loadUI(counties);
                } else {
                    Log.e("CountyFragment", "onComplete Location Error");
                    Toast.makeText(getContext(), "Unable to get current location!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadUI(List<County> counties) {
        countyTextView.setText(counties.get(0).getCountyName());
    }
}
