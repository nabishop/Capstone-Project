package Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class Location {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public Location() {
        locationRequest = setUpLocationRequest();
    }

    private LocationRequest setUpLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000000);
        locationRequest.setSmallestDisplacement(1000);

        return locationRequest;
    }

    private void setUpCallBack(String result) {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                android.location.Location location = locationResult.getLocations().get(0);
                result = getCounty(location.getLatitude(), location.getLongitude());
            }
        };
        return callback;
    }

    private void getCounty(double latitude, double longitude) {

    }
}
