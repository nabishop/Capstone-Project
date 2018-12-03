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
    private static final String LONGITUDE = "lat";
    private static final String FORMAT = "lat";
    private static final String JSON_FORMAT = "json";

    public static String getCountyName(double latitude, double longitude) {
        Uri requesting = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(LATITUDE, String.valueOf(latitude))
                .appendQueryParameter(LONGITUDE, String.valueOf(longitude))
                .appendQueryParameter(FORMAT, JSON_FORMAT)
                .build();
        URL url = Connection.getURL(requesting);
        if (url != null) {
            String response = Connection.urlRequest(url);

            if (response != null)
                return parseResponse(response);
        }
        return null;
    }

    private static String parseResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
            return jsonArray.getString(3);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CountyLoc Parsing Fail", "Exception: " + e);
            return null;
        }
    }

}
