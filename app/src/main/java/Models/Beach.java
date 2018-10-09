package Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Beach implements Parcelable {
    private String spotId;
    private String spotName;
    private String date;
    private String day;
    private double waveSizeFt;
    private int score;
    private ArrayList<String> warnings;


    public Beach(String spotId, String spotName, String date, String day,
                 double waveSizeFt, int score, ArrayList<String> warnings) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.date = date;
        this.day = day;
        this.waveSizeFt = waveSizeFt;
        this.score = score;
        this.warnings = warnings;
    }

    public Beach(Parcel in) {
        this.spotId = in.readString();
        this.spotName = in.readString();
        this.date = in.readString();
        this.day = in.readString();
        this.waveSizeFt = in.readDouble();
        this.score = in.readInt();
        this.warnings = in.readArrayList(String.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spotId);
        dest.writeString(spotName);
        dest.writeString(date);
        dest.writeString(day);
        dest.writeDouble(waveSizeFt);
        dest.writeInt(score);
        dest.writeList(warnings);
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

    public ArrayList<String> getWarnings() {
        return warnings;
    }
}
