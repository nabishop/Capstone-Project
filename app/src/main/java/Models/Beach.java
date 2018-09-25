package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Beach implements Parcelable {
    private String spotId;
    private String spotName;
    private ArrayList<WeatherHour> weatherForTheDay;
    private String waterTemperatureForTheDayCelcius;
    private String waterTemperatureForTheDayDegrees;


    public Beach(String spotId, String spotName, ArrayList<WeatherHour> weatherForTheDay,
                 String waterTemperatureForTheDayCelcius, String waterTemperatureForTheDayDegrees) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.weatherForTheDay = weatherForTheDay;
        this.waterTemperatureForTheDayCelcius = waterTemperatureForTheDayCelcius;
        this.waterTemperatureForTheDayDegrees = waterTemperatureForTheDayDegrees;
    }

    public Beach(Parcel in) {
        this.spotId = in.readString();
        this.spotName = in.readString();
        this.weatherForTheDay = in.readArrayList(WeatherHour.class.getClassLoader());
        this.waterTemperatureForTheDayCelcius = in.readString();
        this.waterTemperatureForTheDayDegrees = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotId);
        dest.writeString(spotName);
        dest.writeList(weatherForTheDay);
        dest.writeString(waterTemperatureForTheDayCelcius);
        dest.writeString(waterTemperatureForTheDayDegrees);
    }

    public static final Creator<Beach> CREATOR = new Creator<Beach>() {
        @Override
        public Beach createFromParcel(Parcel source) {
            return new Beach(source);
        }

        @Override
        public Beach[] newArray(int size) {
            return new Beach[size];
        }
    };

}
