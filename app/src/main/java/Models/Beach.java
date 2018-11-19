package Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Beach implements Comparable {
    private int spotId;
    private String spotName;
    private String date;
    private String day;
    private double waveSizeFt;
    private double score;
    private ArrayList<String> warnings;


    public Beach(int spotId, String spotName, String date, String day,
                 double waveSizeFt, double score, ArrayList<String> warnings) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.date = date;
        this.day = day;
        this.waveSizeFt = waveSizeFt;
        this.score = score;
        this.warnings = warnings;
    }


    public double getScore() {
        return score;
    }

    public int getSpotId() {
        return spotId;
    }

    public String getSpotName() {
        return spotName;
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
