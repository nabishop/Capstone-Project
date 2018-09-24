package com.example.android.rightide.UI;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.android.rightide.R;

public class NavigationBar {
    public static BottomNavigationView.OnNavigationItemSelectedListener createNavigationBar() {
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_favorites:
                        return true;
                    case R.id.navigation_county:
                        return true;
                    case R.id.navigation_explore:
                        return true;
                    case R.id.navigation_settings:
                        return true;
                }
                return false;
            }
        };
        return mOnNavigationItemSelectedListener;
    }
}
