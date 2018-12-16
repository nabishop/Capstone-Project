package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class County implements Comparable, Parcelable {
    private String countyName;
    private ArrayList<Beach> beachesInCounty;
    private double averageScore;

    public County(String countyName, ArrayList<Beach> beachesInCounty) {
        this.countyName = countyName;
        this.beachesInCounty = beachesInCounty;
    }

    public static final Creator<County> CREATOR = new Creator<County>() {
        @Override
        public County createFromParcel(Parcel in) {
            return new County(in);
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

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        County next = (County) o;
        if (next.getAverageScore() > this.getAverageScore())
            return 1;
        else if (next.getAverageScore() < this.getAverageScore())
            return -1;
        return 0;
    }

    protected County(Parcel in) {
        this.countyName = in.readString();
        this.beachesInCounty = in.readArrayList(Beach.class.getClassLoader());
        this.averageScore = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countyName);
        dest.writeList(beachesInCounty);
        dest.writeDouble(averageScore);
    }


}
