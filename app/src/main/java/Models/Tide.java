package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Tide implements Parcelable {
    private String hour;
    private double tideFeet;

    public Tide(String hour, double tideFeet) {
        this.hour = hour;
        this.tideFeet = tideFeet;
    }

    protected Tide(Parcel in) {
        this.hour = in.readString();
        this.tideFeet = in.readDouble();
    }

    public static final Creator<Tide> CREATOR = new Creator<Tide>() {
        @Override
        public Tide createFromParcel(Parcel in) {
            return new Tide(in);
        }

        @Override
        public Tide[] newArray(int size) {
            return new Tide[size];
        }
    };

    public String getHour() {
        return hour;
    }

    public double getTideFeet() {
        return tideFeet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hour);
        parcel.writeDouble(tideFeet);
    }
}
