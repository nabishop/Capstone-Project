package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Beach implements Comparable, Parcelable {
    private int spotId;
    private String beachName;
    private String date;
    private String day;
    private double waveSizeFt;
    private int score;
    private ArrayList<String> warnings;


    public Beach(int spotId, String beachName, String date, String day,
                 double waveSizeFt, int score, ArrayList<String> warnings) {
        this.spotId = spotId;
        this.beachName = beachName;
        this.date = date;
        this.day = day;
        this.waveSizeFt = waveSizeFt;
        this.score = score;
        this.warnings = warnings;
    }


    protected Beach(Parcel in) {
        spotId = in.readInt();
        beachName = in.readString();
        date = in.readString();
        day = in.readString();
        waveSizeFt = in.readDouble();
        score = in.readInt();
        warnings = in.createStringArrayList();
    }

    public static final Creator<Beach> CREATOR = new Creator<Beach>() {
        @Override
        public Beach createFromParcel(Parcel in) {
            return new Beach(in);
        }

        @Override
        public Beach[] newArray(int size) {
            return new Beach[size];
        }
    };

    public int getScore() {
        return score;
    }

    public int getSpotId() {
        return spotId;
    }

    public String getBeachName() {
        return beachName;
    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Beach next = (Beach) o;
        return (int) (next.getScore() - this.getScore());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(spotId);
        dest.writeString(beachName);
        dest.writeString(date);
        dest.writeString(day);
        dest.writeDouble(waveSizeFt);
        dest.writeInt(score);
        dest.writeStringList(warnings);
    }
}
