package com.example.android.rightide.UI;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.rightide.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Adapters.WarningsListBeachDetailAdapter;
import ContentProvider.Contract;
import ContentProvider.CursorHelper;
import ContentProvider.DbHelper;
import Models.Beach;
import Models.County;
import Models.CountyWeatherExtras;
import Models.Swell;
import Models.Tide;
import Models.Wind;
import Utils.WeatherExtrasLoader;

public class BeachDetailFragment extends Fragment {
    County county;
    String beachName;
    CountyWeatherExtras extras;

    ImageView beachImageView;
    TextView beachNameTextView;
    TextView countyNameTextView;
    TextView scoreTextView;
    TextView waveHeightTextView;
    TextView tideTextView;
    TextView windTextView;
    TextView temperatureTextView;
    TextView noWarningsTextView;

    ImageView favorites;
    TextView favoritesTv;
    ImageView maps;
    ImageView notifications;

    static GraphView waveGraphView;
    static GraphView tideGraphView;
    static GraphView windGraphView;

    LinearLayoutManager warningsLinearLayoutManager;
    RecyclerView warningsRecyclerView;

    public BeachDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.beach_detail_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle == null)
            return root;

        county = bundle.getParcelable(CountyFragment.SAVED_BEACHES_STATE);
        beachName = county.getBeachesInCounty().get(0).getBeachName();

        if (savedInstanceState == null) {
            // load extras in background while loading other UI first
            new WeatherExtrasASyncTask().execute(county.getCountyName());
        }
        setUpUI(root);
        loadUI();

        return root;
    }

    private boolean isInDatabase(int id) {
        List<Integer> beachId = CursorHelper.getFavoritedBeacheIds(getContext());

        if (beachId == null) return false;

        for (Integer beachIdIter : beachId) {
            if (id == beachIdIter) {
                return true;
            }
        }
        return false;
    }

    private void loadUI() {
        loadBeachImage();

        loadImageClicks();

        beachNameTextView.setText(county.getBeachesInCounty().get(0).getBeachName());

        String fullLocation = county.getCountyName() + ", California";
        countyNameTextView.setText(fullLocation);

        String rounded = String.format(Locale.ENGLISH, "%.2f", county.getAverageScore()) + "/10";
        scoreTextView.setText(rounded);

        loadWarnings();

        averageBeachStatistics();

        waveHeightTextView.setText(String.format("%s'", String.format(Locale.ENGLISH,
                "%.2f", county.getAverageWaveHeight())));

        tideTextView.setText(String.format("%s/5", String.format(Locale.ENGLISH,
                "%.2f", county.getAverageTideScore())));

        windTextView.setText(String.format("%s/5", String.format(Locale.ENGLISH,
                "%.2f", county.getAverageWindScore())));

        temperatureTextView.setText(
                String.format(Locale.ENGLISH, "%.2f", county.getTemperatureFahrenheit()));

        loadWaveSizeGraphView();
    }

    private void loadImageClicks() {
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddFavorite();
            }
        });

        if (isInDatabase(county.getBeachesInCounty().get(0).getSpotId())) {
            favoritesTv.setText("Unfavorite");
            favorites.setImageDrawable(getResources().getDrawable(R.drawable.star));
        } else {
            favoritesTv.setText("Favorite");
            favorites.setImageDrawable(getResources().getDrawable(R.drawable.star_outline));
        }
    }

    private void onClickAddFavorite() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Contract.BeachEntry.COLUMN_BEACH_NAME, beachName);
        contentValues.put(Contract.BeachEntry.COLUMN_BEACH_COUNTY, county.getCountyName());
        contentValues.put(Contract.BeachEntry.COLUMN_BEACH_ID, county.getBeachesInCounty().get(0).getSpotId());

        if (!isInDatabase(county.getBeachesInCounty().get(0).getSpotId())) {
            getActivity().getContentResolver().insert(Contract.BeachEntry.CONTENT_URI, contentValues);

            favoritesTv.setText("Unfavorite");
            favorites.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Toast.makeText(getContext(), "Added " + beachName + " to Favorites",
                    Toast.LENGTH_SHORT).show();
        } else {
            getActivity().getContentResolver().delete(Contract.BeachEntry.CONTENT_URI, Contract.BeachEntry.COLUMN_BEACH_ID + "=?",
                    new String[]{String.valueOf(county.getBeachesInCounty().get(0).getSpotId())});

            favoritesTv.setText("Favorite");
            favorites.setImageDrawable(getResources().getDrawable(R.drawable.star_outline));

            Toast.makeText(getContext(), "Removed " + beachName + " from Favorites",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBeachImage() {
        Unsplash unsplash = new Unsplash(getResources().getString(R.string.unsplash_auth_key));

        String query = "surf " + county.getBeachesInCounty().get(0).getBeachName() + " " + county.getCountyName();
        unsplash.searchPhotos(query, new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                List<Photo> photos = results.getResults();
                Photo best = photos.get(0);
                Picasso.get().load(best.getUrls().getRegular()).into(beachImageView);
            }

            @Override
            public void onError(String error) {
                beachImageView.setImageResource(R.drawable.county_test);
                Toast.makeText(getContext(), "Sorry! No Image could be Found! Here is a Nice One Though!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadWaveSizeGraphView() {
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>();
        waveGraphView.getViewport().setXAxisBoundsManual(true);
        waveGraphView.getViewport().setMaxX(county.getBeachesInCounty().size() - 1);

        waveGraphView.getGridLabelRenderer().setPadding(4);
        waveGraphView.getGridLabelRenderer().setVerticalAxisTitle("Wave Size (Feet)");
        waveGraphView.getGridLabelRenderer().setHorizontalAxisTitle("Time Today (Hour)");

        Log.d("BeachDetailFragment", "Size is " + county.getBeachesInCounty().size());
        for (int x = 0; x < county.getBeachesInCounty().size(); x++) {
            lineGraphSeries.appendData(new DataPoint(x, county.getBeachesInCounty().get(x).getWaveSizeFt()),
                    true, county.getBeachesInCounty().size());
        }
        lineGraphSeries.setDataPointsRadius(10);
        lineGraphSeries.setDrawDataPoints(true);

        waveGraphView.addSeries(lineGraphSeries);
    }

    private void loadWarnings() {
        ArrayList<Beach> warningHours = new ArrayList<>();
        for (Beach beach : county.getBeachesInCounty()) {
            if (!beach.getWarnings().isEmpty()) {
                warningHours.add(beach);
            }
        }
        if (!warningHours.isEmpty()) {
            WarningsListBeachDetailAdapter adapter = new WarningsListBeachDetailAdapter();
            adapter.setWarnings(warningHours);

            noWarningsTextView.setVisibility(View.GONE);
            warningsRecyclerView.setVisibility(View.VISIBLE);

            warningsRecyclerView.setAdapter(adapter);
        }
    }

    private void setUpUI(View root) {
        beachImageView = root.findViewById(R.id.beach_detail_fragment_image);
        beachNameTextView = root.findViewById(R.id.beach_detail_fragment_name);
        countyNameTextView = root.findViewById(R.id.beach_detail_fragment_county_name);
        scoreTextView = root.findViewById(R.id.beach_detail_fragment_score);
        waveHeightTextView = root.findViewById(R.id.beach_detail_fragment_wave_height);
        tideTextView = root.findViewById(R.id.beach_detail_fragment_tide);
        windTextView = root.findViewById(R.id.beach_detail_fragment_wind);
        temperatureTextView = root.findViewById(R.id.beach_detail_fragment_temperature);
        waveGraphView = root.findViewById(R.id.beach_detail_fragment_wave_graph);
        tideGraphView = root.findViewById(R.id.beach_detail_fragment_tide_graph);
        windGraphView = root.findViewById(R.id.beach_detail_fragment_wind_graph);
        noWarningsTextView = root.findViewById(R.id.beach_detail_fragment_no_warnings);

        warningsLinearLayoutManager = new LinearLayoutManager(getContext());
        warningsRecyclerView = root.findViewById(R.id.beach_detail_fragment_warnings_rv);
        warningsRecyclerView.setLayoutManager(warningsLinearLayoutManager);
        warningsRecyclerView.setHasFixedSize(true);

        favorites = root.findViewById(R.id.detail_star_favorite_image);
        favoritesTv = root.findViewById(R.id.detail_star_favorite_textView);
        maps = root.findViewById(R.id.detail_map_image);
        notifications = root.findViewById(R.id.detail_notification_image);
    }

    private void averageBeachStatistics() {
        double waveAvg = 0, tideAvg = 0, windAvg = 0;

        for (int x = 0; x < county.getBeachesInCounty().size(); x++) {
            waveAvg += county.getBeachesInCounty().get(x).getWaveSizeFt();
            tideAvg += county.getBeachesInCounty().get(x).getTideScore();
            windAvg += county.getBeachesInCounty().get(x).getWindScore();
        }
        waveAvg = waveAvg / county.getBeachesInCounty().size();
        tideAvg = tideAvg / county.getBeachesInCounty().size();
        windAvg = windAvg / county.getBeachesInCounty().size();

        county.setAverageWaveHeight(waveAvg);
        county.setAverageTideScore(tideAvg);
        county.setAverageWindScore(windAvg);
    }

    private static class WeatherExtrasASyncTask extends AsyncTask<String, Void, CountyWeatherExtras> {

        @Override
        protected CountyWeatherExtras doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }

            return WeatherExtrasLoader.getCountyWeatherExtras(strings[0]);
        }

        @Override
        protected void onPostExecute(CountyWeatherExtras countyWeatherExtras) {
            super.onPostExecute(countyWeatherExtras);
            loadTideGraph(countyWeatherExtras.getTideDay());
            loadWindGraph(countyWeatherExtras.getWindDay());
            loadSwellGraph(countyWeatherExtras.getSwellDay());
        }

        private void loadTideGraph(ArrayList<Tide> tides) {
            LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>();
            tideGraphView.getViewport().setXAxisBoundsManual(true);
            tideGraphView.getViewport().setMaxX(tides.size() - 1);

            tideGraphView.getGridLabelRenderer().setPadding(4);
            tideGraphView.getGridLabelRenderer().setVerticalAxisTitle("Tide Size (Feet)");
            tideGraphView.getGridLabelRenderer().setHorizontalAxisTitle("Time Today (Hour)");

            for (int x = 0; x < tides.size(); x++) {
                lineGraphSeries.appendData(new DataPoint(x, tides.get(x).getTideFeet()),
                        true, tides.size());
            }
            lineGraphSeries.setDataPointsRadius(10);
            lineGraphSeries.setDrawDataPoints(true);

            tideGraphView.addSeries(lineGraphSeries);
        }

        private void loadSwellGraph(ArrayList<Swell> swells) {

        }

        private void loadWindGraph(ArrayList<Wind> winds) {
            LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>();
            windGraphView.getViewport().setXAxisBoundsManual(true);
            windGraphView.getViewport().setMaxX(winds.size() - 1);

            windGraphView.getGridLabelRenderer().setPadding(4);
            windGraphView.getGridLabelRenderer().setVerticalAxisTitle("Wind Speed (Mph)");
            windGraphView.getGridLabelRenderer().setHorizontalAxisTitle("Time today (hour)");

            for (int x = 0; x < winds.size(); x++) {
                lineGraphSeries.appendData(new DataPoint(x, winds.get(x).getWindSpeedMph()),
                        true, winds.size());
            }
            lineGraphSeries.setDataPointsRadius(10);
            lineGraphSeries.setDrawDataPoints(true);

            windGraphView.addSeries(lineGraphSeries);
        }

    }
}
