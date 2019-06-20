package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class County implements Comparable, Parcelable {
    private String countyName;
    private ArrayList<Beach> beachesInCounty;
    private double averageScore;
    private double averageWaveHeight;
    private double averageTideScore;
    private double averageWindScore;
    private double temperatureFahrenheit;
    private String wetSuit;
    private double latitude;
    private double longitude;

    public County(String countyName, ArrayList<Beach> beachesInCounty,
                  double temperatureFahrenheit, String wetSuit, double latitude, double longitude) {
        this.countyName = countyName;
        this.beachesInCounty = beachesInCounty;
        this.temperatureFahrenheit = temperatureFahrenheit;
        this.wetSuit = wetSuit;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getAverageTideScore() {
        return averageTideScore;
    }

    public double getAverageWindScore() {
        return averageWindScore;
    }

    public double getTemperatureFahrenheit() {
        return temperatureFahrenheit;
    }

    public void setAverageTideScore(double averageTideScore) {
        this.averageTideScore = averageTideScore;
    }

    public void setAverageWindScore(double averageWindScore) {
        this.averageWindScore = averageWindScore;
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

    public double getAverageWaveHeight() {
        return averageWaveHeight;
    }

    public void setAverageWaveHeight(double averageWaveHeight) {
        this.averageWaveHeight = averageWaveHeight;
    }

    public String getWetSuit() {
        return wetSuit;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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
        this.averageWaveHeight = in.readDouble();
        this.averageTideScore = in.readDouble();
        this.averageWindScore = in.readDouble();
        this.temperatureFahrenheit = in.readDouble();
        this.wetSuit = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
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
        dest.writeDouble(averageWaveHeight);
        dest.writeDouble(averageTideScore);
        dest.writeDouble(averageWindScore);
        dest.writeDouble(temperatureFahrenheit);
        dest.writeString(wetSuit);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }


}
