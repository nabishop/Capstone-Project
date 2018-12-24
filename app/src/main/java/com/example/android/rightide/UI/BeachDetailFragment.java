package com.example.android.rightide.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rightide.R;
import com.jjoe64.graphview.GraphView;

import java.util.Locale;

import Models.County;

public class BeachDetailFragment extends Fragment {
    County county;
    String beachName;

    TextView beachNameTextView;
    TextView countyNameTextView;
    TextView scoreTextView;
    TextView waveHeightTextView;
    TextView tideTextView;
    TextView windTextView;
    TextView temperatureTextView;

    GraphView waveGraphView;
    GraphView tideGraphView;
    GraphView windGraphView;

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

        getActivity().setTitle(getActivity().getTitle() + " - " + beachName);

        setUpUI(root);
        loadUI();

        return root;
    }

    private void loadUI() {
        beachNameTextView.setText(county.getBeachesInCounty().get(0).getBeachName());

        String fullLocation = county.getCountyName() + ", California";
        countyNameTextView.setText(fullLocation);

        String rounded = String.format(Locale.ENGLISH, "%.2f", county.getAverageScore()) + "/10";
        scoreTextView.setText(rounded);

        averageBeachStatistics();

        waveHeightTextView.setText(String.format("%s'", String.format(Locale.ENGLISH,
                "%.2f", county.getAverageWaveHeight())));

        tideTextView.setText(String.format("%s/4", String.format(Locale.ENGLISH,
                "%.2f", county.getAverageTideScore())));

        windTextView.setText(String.format("%s/4", String.format(Locale.ENGLISH,
                "%.2f", county.getAverageWindScore())));

        temperatureTextView.setText(
                String.format(Locale.ENGLISH, "%.2f", county.getTemperatureFahrenheit()));

    }

    private void setUpUI(View root) {
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
}
