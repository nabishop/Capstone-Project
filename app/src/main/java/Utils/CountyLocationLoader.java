package Utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import Models.County;

public class CountyLocationLoader {
    private static final String RESULTS = "results";
    private static final String COUNTY_NAME = "county_name";
    private static final String BASE_URL = "https://geo.fcc.gov/api/census/area";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String FORMAT = "format";
    private static final String JSON_FORMAT = "json";
    private static final String TAG = "CountyLocationLoader";

    public static String getCountyName(double latitude, double longitude) {
        Uri requesting = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(LATITUDE, String.valueOf(latitude))
                .appendQueryParameter(LONGITUDE, String.valueOf(longitude))
                .appendQueryParameter(FORMAT, JSON_FORMAT)
                .build();
        Log.d(TAG, "URI is " + requesting.toString());
        URL url = Connection.getURL(requesting);
        Log.d(TAG, "URL is " + requesting.toString());
        if (url != null) {
            String response = Connection.urlRequest(url);
            Log.d(TAG, "response is " + response);

            if (response != null)
                return parseResponse(response);
        }
        Log.d(TAG, "Returning null from getCountyName :(");
        return null;
    }

    private static String parseResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
            JSONObject nameObj = jsonArray.getJSONObject(0);
            Log.d("CountyName", nameObj.getString(COUNTY_NAME));
            return nameObj.getString(COUNTY_NAME);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CountyLoc Parsing Fail", "Exception: " + e);
            return null;
        }
    }

}
