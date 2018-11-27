package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Beach implements Comparable {
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
}
