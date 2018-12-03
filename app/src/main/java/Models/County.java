package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class County implements Comparable {
    private String countyName;
    private ArrayList<Beach> beachesInCounty;
    private double averageScore;

    public County(String countyName, ArrayList<Beach> beachesInCounty) {
        this.countyName = countyName;
        this.beachesInCounty = beachesInCounty;
    }

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
        return (int) (next.getAverageScore() - this.getAverageScore());
    }
}
