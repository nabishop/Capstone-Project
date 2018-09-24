package com.example.android.rightide.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.rightide.R;

public class CountyActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.county_activity);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mOnNavigationItemSelectedListener = NavigationBar.createNavigationBar();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setUpCountyFragment();
    }

    public void setUpCountyFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.county_activity_county_fragment, new CountyFragment(), null).commit();
    }
}
