package Utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Models.Beach;
import Models.County;

import static Utils.Connection.getURL;
import static Utils.Connection.urlRequest;

// a bunch of helper strings used for parsing
public class CountyLoader {
    private static final String TAG = "COUNTY_LOADER";

    private static class JSONParsing {
        public static final String SPOTS_URL = "http://api.spitcast.com/api/county/spots";
        public static final String[] COUNTIES = {"Del Norte", "Humboldt", "Mendocino",
                "Sonoma", "Marin", "San Fransisco", "Santa Cruz", "San Mateo", "Monterey",
                "San Luis Obispo", "Santa Barbara", "Ventura", "Los Angeles",
                "Orange County", "San Diego"};
        public static final String COUNTY = "county";
        public static final String SPOT_ID = "spot_id";
        public static final String SPOT_NAME = "spot_name";

        public static final String TEMP_URL = "http://api.spitcast.com/api/county/water-temperature";
        public static final String TEMP_FARH_KEY = "fahrenheit";
        public static final String WETSUIT_KEY = "wetsuit";
    }

    private static ArrayList results;

    // public helper function that returns a list of counties.
    // takes in a county name and returns a list of the beaches
    // in that county, using other helper functions to get certain info
    // about the beaches and what not
    public static List<County> getCountyList(String countyName) {
        if (countyName != null) {
            countyName = countyName.replace(" ", "-");
            countyName = countyName.toLowerCase();

            Uri requesting = Uri.parse(CountyLoader.JSONParsing.SPOTS_URL).buildUpon()
                    .appendPath(countyName).build();
            Log.d(TAG, "Uri is " + requesting.toString());
            URL countyRequest = getURL(requesting);
            Log.d(TAG, "Url is " + countyRequest.toString());
            String response = Connection.urlRequest(countyRequest);

            if (response != null) {
                List<County> countyList = parseResponse(response);
                if (countyList != null)
                    return countyList;
            }

        }
        return null;
    }

    private static List<County> parseResponse(String response) {
        try {
            JSONArray results = new JSONArray(response);
            List<County> countyList = new ArrayList<>();

            for (int x = 0; x < results.length(); x++) {
                JSONObject countyObject = results.getJSONObject(x);
                County newCounty = countyBuilder(countyObject);

                double tempScore = 0;

                ArrayList<Beach> beaches = newCounty.getBeachesInCounty();
                int inner = 0;
                for (inner = 0; inner < beaches.size(); inner++) {
                    //Log.d("Parse Resoibse", "Temp score is INNER " + beaches.get(inner).getScore());
                    tempScore += beaches.get(inner).getScore();
                }
                //Log.d("Parse Resoibse", "Temp score is " + tempScore + " inner is " + inner);
                newCounty.setAverageScore(tempScore / beaches.size());
                countyList.add(newCounty);
            }
            return countyList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CountyLoader Parsing", "Exception: " + e);
            return null;
        }
    }

    private static ArrayList getTempNWetSuit(String countyName) throws JSONException {
        ArrayList results = new ArrayList();
        countyName = countyName.replace(" ", "-");
        countyName = countyName.toLowerCase();

        Uri requesting = Uri.parse(JSONParsing.TEMP_URL).buildUpon().appendPath(countyName).build();
        URL tempRequest = getURL(requesting);

        if (tempRequest != null) {
            String response = urlRequest(tempRequest);

            if (response != null) {
                JSONObject tempObject = new JSONObject(response);

                results.add(tempObject.getDouble(JSONParsing.TEMP_FARH_KEY));
                results.add(tempObject.getString(JSONParsing.WETSUIT_KEY));
                //Log.e("getTempNWetSuit", "added temp " + tempObject.getDouble(JSONParsing.TEMP_FARH_KEY));
                //Log.e("getTempNWetSuit", "added suit " + tempObject.getString(JSONParsing.WETSUIT_KEY));
                return results;
            }
        }
        Log.e("getTempNWetSuit", "returning null :(");
        return null;
    }

    // builds the county, uses parsing information for the beach spot_ids to
    // make the counry objects.
    private static County countyBuilder(JSONObject countyObject) {
        try {
            String countyName = countyObject.getString(JSONParsing.COUNTY);
            int spot_id = countyObject.getInt(JSONParsing.SPOT_ID);
            String spotName = countyObject.getString(JSONParsing.SPOT_NAME);

            ArrayList<Beach> beachArrayList;
            beachArrayList = BeachLoader.getBeach(spotName, spot_id);

            if(results==null){
                results = getTempNWetSuit(countyName);
            }

            County county;
            if (results == null) {
                county = new County(countyName, beachArrayList, 0, "No Data Available");
            } else {
                county = new County(countyName, beachArrayList,
                        (double) results.get(0), (String) results.get(1));
            }
            return county;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("countyBuilder Failure", "Exception for county: " + e);
            return null;
        }
    }
}
