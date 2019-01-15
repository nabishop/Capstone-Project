package com.example.android.rightide.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.android.rightide.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.AutoCompleteCountyAdapter;
import Models.CountyAutoTextViewItem;

public class ExploreFragment extends Fragment {
    private List<CountyAutoTextViewItem> autoTextViewItem;
    private CountyAutoTextViewItem clickedItem;

    public ExploreFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.explore_fragment, container, false);

        fillCountyList();
        setUpAutoCompleteTextView(root);

        return root;
    }

    private void setUpAutoCompleteTextView(View root) {
        AutoCompleteTextView editText = root.findViewById(R.id.explore_fragment_autocomplete_tv);
        AutoCompleteCountyAdapter autoCompleteCountyAdapter = new AutoCompleteCountyAdapter(getContext(), autoTextViewItem);

        editText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clickedItem = (CountyAutoTextViewItem) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editText.setAdapter(autoCompleteCountyAdapter);

    }

    private void fillCountyList() {
        autoTextViewItem = new ArrayList<>();
        autoTextViewItem.add(new CountyAutoTextViewItem("Marin", R.drawable.marin));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Fransisco", R.drawable.san_fransisco));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Mateo", R.drawable.san_mateo));
        autoTextViewItem.add(new CountyAutoTextViewItem("Santa Cruz", R.drawable.santa_cruz));
        autoTextViewItem.add(new CountyAutoTextViewItem("Monterey", R.drawable.monterey));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Luis Obispo", R.drawable.san_luis_obispo));
        autoTextViewItem.add(new CountyAutoTextViewItem("Ventura", R.drawable.ventura));
        autoTextViewItem.add(new CountyAutoTextViewItem("Los Angeles", R.drawable.los_angeles));
        autoTextViewItem.add(new CountyAutoTextViewItem("Orange County", R.drawable.orange_county));
        autoTextViewItem.add(new CountyAutoTextViewItem("San Diego", R.drawable.san_diego));
    }
}
