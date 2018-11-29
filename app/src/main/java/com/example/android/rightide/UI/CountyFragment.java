package com.example.android.rightide.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.rightide.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import Utils.CountyLocationLoader;

public class CountyFragment extends Fragment {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public CountyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.county_fragment, container, false);

        setUpLocationRequest();
        setUpCallBack();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MasterActivity.REQUEST_LOCATION);
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        return root;
    }

    private void setUpLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000000);
        locationRequest.setSmallestDisplacement(1000);
    }

    private void setUpCallBack() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                String county;

                super.onLocationResult(locationResult);
                android.location.Location location = locationResult.getLocations().get(0);
                county = CountyLocationLoader.getCountyName(location.getLatitude(), location.getLongitude());
                loadUI(county);
            }
        };
    }

    private void loadUI(String county) {

    }

    private String getCounty(double latitude, double longitude) {
        String county = new String();


        return county;
    }
}
