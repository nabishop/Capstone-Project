package com.example.android.rightide.UI;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.rightide.R;

import java.util.ArrayList;

import ContentProvider.BeachContentProvider;
import ContentProvider.Contract;
import ContentProvider.CursorHelper;

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

        ArrayList<Integer> currentFavoritedBeaches = CursorHelper.getFavoritedBeacheIds(this);
        if (currentFavoritedBeaches != null)
            setUpFavoritesFragment(currentFavoritedBeaches);
        else
            setUpCountyFragment();
    }

    public BottomNavigationView.OnNavigationItemSelectedListener createNavigationBar(final Context context) {
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_favorites:
                        setUpFavoritesFragment(CursorHelper.getFavoritedBeacheIds(context));
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
        if (getSupportFragmentManager().findFragmentByTag(ActiveFragmentTags.TAG_COUNTY_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new CountyFragment(), null).commit();
        }
    }

    public void setUpFavoritesFragment(ArrayList<Integer> beachIds) {
        if (getSupportFragmentManager().findFragmentByTag(ActiveFragmentTags.TAG_FAVORITES_FRAGMENT) == null) {
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList(ActiveFragmentTags.TAG_FAVORITES_LIST, beachIds);

            FavoitesFragment favoitesFragment = new FavoitesFragment();
            favoitesFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, favoitesFragment, null).commit();
        }
    }

    public void setUpExploreFragment() {
        if (getSupportFragmentManager().findFragmentByTag(ActiveFragmentTags.TAG_EXPLORE_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new ExploreFragment(), null).commit();
        }
    }

    public void setUpSettingsFragment() {
        if (getSupportFragmentManager().findFragmentByTag(ActiveFragmentTags.TAG_SETTINGS_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.master_activity_fragment, new SettingsFragment(), null).commit();
        }
    }
}
