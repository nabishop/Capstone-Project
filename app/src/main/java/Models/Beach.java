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
    private double score;
    private int tideScore;
    private int swellScore;
    private int windScore;
    private String hour;

    private ArrayList<String> warnings;


    public Beach(int spotId, String beachName, String date, String day,
                 double waveSizeFt, double score, ArrayList<String> warnings,
                 int tideScore, int swellScore, int windScore, String hour) {
        this.spotId = spotId;
        this.beachName = beachName;
        this.date = date;
        this.day = day;
        this.waveSizeFt = waveSizeFt;
        this.score = score;
        this.warnings = warnings;
        this.tideScore = tideScore;
        this.swellScore = swellScore;
        this.windScore = windScore;
        this.hour = hour;
    }


    protected Beach(Parcel in) {
        spotId = in.readInt();
        beachName = in.readString();
        date = in.readString();
        day = in.readString();
        waveSizeFt = in.readDouble();
        score = in.readDouble();
        warnings = in.createStringArrayList();
        tideScore = in.readInt();
        swellScore = in.readInt();
        windScore = in.readInt();
        hour = in.readString();
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

    public double getScore() {
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

    public int getSwellScore() {
        return swellScore;
    }

    public int getTideScore() {
        return tideScore;
    }

    public int getWindScore() {
        return windScore;
    }

    public double getWaveSizeFt() {
        return waveSizeFt;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
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
        dest.writeDouble(score);
        dest.writeStringList(warnings);
        dest.writeInt(tideScore);
        dest.writeInt(swellScore);
        dest.writeInt(windScore);
        dest.writeString(hour);
    }
}
