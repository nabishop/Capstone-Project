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

import Models.County;

public class BeachDetailFragment extends Fragment {
    County county;
    String beachName;

    TextView beachNameTextView;

    public BeachDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.beach_detail_fragment, container, false);

        Bundle bundle = this.getArguments();
        county = bundle.getParcelable(CountyFragment.SAVED_BEACHES_INSTANCE_KEY);
        beachName = county.getBeachesInCounty().get(0).getBeachName();

        getActivity().setTitle(getActivity().getTitle() + " - " + beachName);

        setUpUI(root);
        loadUI();

        return root;
    }

    private void loadUI() {
        beachNameTextView.setText(county.getBeachesInCounty().get(0).getBeachName());
    }

    private void setUpUI(View root) {
        beachNameTextView = root.findViewById(R.id.beach_detail_fragment_name);
    }
}
