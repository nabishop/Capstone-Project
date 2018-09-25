package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherHour implements Parcelable {
    private String swellRating;
    private String tideRating;
    private String windRating;
    private String waveSizeFeet;
    private String hour;

    public WeatherHour(String swellRating, String tideRating, String windRating,
                       String waveSizeFeet, String hour) {
        this.swellRating = swellRating;
        this.tideRating = tideRating;
        this.windRating = windRating;
        this.waveSizeFeet = waveSizeFeet;
        this.hour = hour;
    }


    protected WeatherHour(Parcel in) {
        swellRating = in.readString();
        tideRating = in.readString();
        windRating = in.readString();
        waveSizeFeet = in.readString();
        hour = in.readString();
    }

    public static final Creator<WeatherHour> CREATOR = new Creator<WeatherHour>() {
        @Override
        public WeatherHour createFromParcel(Parcel in) {
            return new WeatherHour(in);
        }

        @Override
        public WeatherHour[] newArray(int size) {
            return new WeatherHour[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(swellRating);
        dest.writeString(tideRating);
        dest.writeString(windRating);
        dest.writeString(waveSizeFeet);
        dest.writeString(hour);
    }

    public String getHour() {
        return hour;
    }

    public String getSwellRating() {
        return swellRating;
    }

    public String getTideRating() {
        return tideRating;
    }

    public String getWaveSizeFeet() {
        return waveSizeFeet;
    }

    public String getWindRating() {
        return windRating;
    }
}
