package Utils;

import java.util.ArrayList;

public class CountyLoader {
    private static class JSONParsing {
        public static final String URL = "http://api.spitcast.com/api/county/spots";
        public static final String[] COUNTIES = {"Del Norte", "Humboldt", "Mendocino",
                "Sonoma", "Marin", "San Fransisco", "Santa Cruz", "San Mateo", "Monterey", "San Luis Obispo",
                "Santa Barbara", "Ventura", "Los Angeles", "Orange County", "San Diego"};
        public static final String COUNTY = "county";
        public static final String SPOT_ID = "spot_id";
        public static final String SPOT_NAME = "spot_name";
    }

    
}
