package com.example.android.rightide.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.rightide.R;

public class MasterActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_activity);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mOnNavigationItemSelectedListener = createNavigationBar(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(getResources().getInteger(R.integer.favorites_position));
        menuItem.setChecked(true);

        setUpFavoritesFragment();
    }

    public BottomNavigationView.OnNavigationItemSelectedListener createNavigationBar(final Context context) {
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_favorites:
                        setUpFavoritesFragment();
                        return true;
                    case R.id.navigation_county:
                        setUpCountyFragment();
                        return true;
                    case R.id.navigation_explore:
                        setUpExploreFragment();
                        return true;
                    case R.id.navigation_settings:
                        setUpSettingsFragment();
                        return true;
                }
                return false;
            }
        };
        return mOnNavigationItemSelectedListener;
    }

    public void setUpCountyFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new CountyFragment(), null).commit();
    }

    public void setUpFavoritesFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new FavoitesFragment(), null).commit();
    }

    public void setUpExploreFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new ExploreFragment(), null).commit();
    }

    public void setUpSettingsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new SettingsFragment(), null).commit();
    }
}
