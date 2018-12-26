package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CountyWeatherExtras implements Parcelable {
    private ArrayList<Swell> swellDay;
    private ArrayList<Tide> tideDay;
    private ArrayList<Wind> windDay;

    public CountyWeatherExtras() {
        swellDay = new ArrayList<>();
        tideDay = new ArrayList<>();
        windDay = new ArrayList<>();
    }

    protected CountyWeatherExtras(Parcel in) {
        swellDay = in.createTypedArrayList(Swell.CREATOR);
        tideDay = in.createTypedArrayList(Tide.CREATOR);
        windDay = in.createTypedArrayList(Wind.CREATOR);
    }

    public static final Creator<CountyWeatherExtras> CREATOR = new Creator<CountyWeatherExtras>() {
        @Override
        public CountyWeatherExtras createFromParcel(Parcel in) {
            return new CountyWeatherExtras(in);
        }

        @Override
        public CountyWeatherExtras[] newArray(int size) {
            return new CountyWeatherExtras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(swellDay);
        parcel.writeTypedList(tideDay);
        parcel.writeTypedList(windDay);
    }

    public ArrayList<Swell> getSwellDay() {
        return swellDay;
    }

    public ArrayList<Tide> getTideDay() {
        return tideDay;
    }

    public ArrayList<Wind> getWindDay() {
        return windDay;
    }
}
