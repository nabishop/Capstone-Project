package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Beach implements Parcelable {
    private String spotId;
    private String spotName;
    private int score;
    private String waterTemperatureForTheDayCelcius;
    private String waterTemperatureForTheDayDegrees;


    public Beach(String spotId, String spotName, int score,
                 String waterTemperatureForTheDayCelcius, String waterTemperatureForTheDayDegrees) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.score = score;
        this.waterTemperatureForTheDayCelcius = waterTemperatureForTheDayCelcius;
        this.waterTemperatureForTheDayDegrees = waterTemperatureForTheDayDegrees;
    }

    public Beach(Parcel in) {
        this.spotId = in.readString();
        this.spotName = in.readString();
        this.score = in.readInt();
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
        dest.writeInt(score);
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

    public int getScore() {
        return score;
    }

    public String getSpotId() {
        return spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public String getWaterTemperatureForTheDayCelcius() {
        return waterTemperatureForTheDayCelcius;
    }

    public String getWaterTemperatureForTheDayDegrees() {
        return waterTemperatureForTheDayDegrees;
    }
}
