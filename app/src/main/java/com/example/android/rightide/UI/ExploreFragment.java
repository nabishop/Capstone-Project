package com.example.android.rightide.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.android.rightide.R;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Adapters.AutoCompleteCountyAdapter;
import Adapters.CountyListBeachAdapter;
import Models.County;
import Models.CountyAutoTextViewItem;
import Utils.CountyLoader;

public class ExploreFragment extends Fragment {
    private List<CountyAutoTextViewItem> autoTextViewItems;
    private static List<County> beachesInCounty;
    private static String clickedCounty;
    private static FragmentManager fragmentManager;
    private static View view;
    private static Context context;

    private final String SAVE_AUTO_LIST = "saved_auto_text_view_list";
    private final String SAVE_CLICKED_COUNTY = "saved_clicked_county";
    private final String SAVE_COUNTIES_SEARCHED = "saved_counties_clicked";

    public ExploreFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(SAVE_CLICKED_COUNTY, clickedCounty);
        outState.putParcelableArrayList(SAVE_AUTO_LIST, (ArrayList<? extends Parcelable>) autoTextViewItems);
        outState.putParcelableArrayList(SAVE_COUNTIES_SEARCHED, (ArrayList<? extends Parcelable>) beachesInCounty);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.explore_fragment, container, false);
        view = root;
        fragmentManager = getFragmentManager();

        if (savedInstanceState == null) {
            fillCountyList();
            setUpAutoCompleteTextView(root);
        } else {
            clickedCounty = savedInstanceState.getString(SAVE_CLICKED_COUNTY);
            autoTextViewItems = savedInstanceState.getParcelableArrayList(SAVE_AUTO_LIST);
            beachesInCounty = savedInstanceState.getParcelableArrayList(SAVE_CLICKED_COUNTY);
        }

        return root;
    }

    private static void loadUI() {
        Log.d("ExploreFrag", "clickedCounty is " + clickedCounty);
        Log.d("ExploreFrag", "beaches is " + beachesInCounty);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        RecyclerView recyclerView = view.findViewById(R.id.explore_fragment_county_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        CountyListBeachAdapter countyListBeachAdapter = new CountyListBeachAdapter();
        countyListBeachAdapter.setBeachesAndContext(beachesInCounty, fragmentManager);

        recyclerView.setAdapter(countyListBeachAdapter);
    }

    private void setUpAutoCompleteTextView(final View root) {
        AutoCompleteTextView editText = root.findViewById(R.id.explore_fragment_autocomplete_tv);
        AutoCompleteCountyAdapter autoCompleteCountyAdapter = new AutoCompleteCountyAdapter(getContext(), autoTextViewItems);

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CountyAutoTextViewItem clickedItem = (CountyAutoTextViewItem) adapterView.getItemAtPosition(i);
                clickedCounty = clickedItem.getCountyName();
                Log.d("ExploreFrag", "Selected " + clickedCounty);

                new ExploreFragment.CountyLoader().execute(clickedCounty);
            }
        });
        Log.d("ExploreFrag", "Set Adapter");
        editText.setAdapter(autoCompleteCountyAdapter);
    }

    private void fillCountyList() {
        autoTextViewItems = new ArrayList<>();
        autoTextViewItems.add(new CountyAutoTextViewItem("Marin", R.drawable.marin));
        autoTextViewItems.add(new CountyAutoTextViewItem("San Fransisco", R.drawable.san_fransisco));
        autoTextViewItems.add(new CountyAutoTextViewItem("San Mateo", R.drawable.san_mateo));
        autoTextViewItems.add(new CountyAutoTextViewItem("Santa Cruz", R.drawable.santa_cruz));
        autoTextViewItems.add(new CountyAutoTextViewItem("Monterey", R.drawable.monterey));
        autoTextViewItems.add(new CountyAutoTextViewItem("San Luis Obispo", R.drawable.san_luis_obispo));
        autoTextViewItems.add(new CountyAutoTextViewItem("Ventura", R.drawable.ventura));
        autoTextViewItems.add(new CountyAutoTextViewItem("Los Angeles", R.drawable.los_angeles));
        autoTextViewItems.add(new CountyAutoTextViewItem("Orange County", R.drawable.orange_county));
        autoTextViewItems.add(new CountyAutoTextViewItem("San Diego", R.drawable.san_diego));
    }

    private static class CountyLoader extends AsyncTask<String, Void, List<County>> {
        @Override
        protected List<County> doInBackground(String... strings) {
            if (strings == null || strings.length < 1 || strings[0] == null)
                return null;

            Log.d("ExploreFrag", "County name is " + strings[0]);
            return Utils.CountyLoader.getCountyList(strings[0]);
        }

        @Override
        protected void onPostExecute(List<County> counties) {
            super.onPostExecute(counties);
            beachesInCounty = new ArrayList<>(counties);

            Log.d("ExploreFrag", "County is " + beachesInCounty);
            loadUI();
        }
    }
}
