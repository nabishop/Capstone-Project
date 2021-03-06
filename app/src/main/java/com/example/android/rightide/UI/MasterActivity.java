package com.example.android.rightide.UI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.rightide.R;

import java.util.ArrayList;

import ContentProvider.BeachContentProvider;
import ContentProvider.Contract;
import ContentProvider.CursorHelper;
import ContentProvider.DbHelper;

public class MasterActivity extends AppCompatActivity {
    public static final int REQUEST_LOCATION = 1000;
    private static final String PERMISSION_ACCEPTED_MESSAGE = "Thanks for Making The App Easier to Use!";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    Menu menu;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_activity);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        mOnNavigationItemSelectedListener = createNavigationBar(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menu = navigation.getMenu();

        if (savedInstanceState == null) {
            dbHelper = new DbHelper(getApplicationContext());
            // check location permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            // if there are any favorite beaches, show favorite page first, else launch county page
            ArrayList<Integer> currentFavoritedBeaches = CursorHelper.getFavoritedBeacheIds(this);
            if (currentFavoritedBeaches != null && currentFavoritedBeaches.size() > 0) {
                MenuItem menuItem = menu.getItem(getResources().getInteger(R.integer.favorites_position));
                menuItem.setChecked(true);
                setUpFavoritesFragment(currentFavoritedBeaches);
            } else {
                MenuItem menuItem = menu.getItem(getResources().getInteger(R.integer.county_position));
                menuItem.setChecked(true);
                setUpCountyFragment();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                Toast.makeText(getApplicationContext(), PERMISSION_ACCEPTED_MESSAGE, Toast.LENGTH_LONG).show();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
