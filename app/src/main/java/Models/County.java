package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class County implements Parcelable {
    private String countyName;
    private ArrayList<Beach> beachesInCounty;

    public County(String countyName, ArrayList<Beach> beachesInCounty) {
        this.countyName = countyName;
        this.beachesInCounty = beachesInCounty;
    }

    public County(Parcel in) {
        this.countyName = in.readString();
        this.beachesInCounty = in.readArrayList(Beach.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countyName);
        dest.writeList(beachesInCounty);
    }

    public static final Creator<County> CREATOR
            = new Creator<County>() {
        @Override
        public County createFromParcel(Parcel source) {
            return new County(source);
        }

        @Override
        public County[] newArray(int size) {
            return new County[size];
        }
    };

    public ArrayList<Beach> getBeachesInCounty() {
        return beachesInCounty;
    }

    public String getCountyName() {
        return countyName;
    }
}
