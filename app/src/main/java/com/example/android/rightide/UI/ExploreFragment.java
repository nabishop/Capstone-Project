package com.example.android.rightide.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.rightide.R;

import java.util.ArrayList;
import java.util.List;

import Models.CountyAutoTextViewItem;

public class ExploreFragment extends Fragment {
    private List<CountyAutoTextViewItem> autoTextViewItem;

    public ExploreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.explore_fragment, container, false);

        fillCountyList();

        return root;
    }

    private void fillCountyList() {
        autoTextViewItem = new ArrayList<>();
        autoTextViewItem.add(new CountyAutoTextViewItem("Marin", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Fransisco", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Mateo", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("Santa Cruz", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("Monterey", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Luis Obispo", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("Ventura", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("Los Angeles", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("Orange County", ));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Diego", ));
    }
}
