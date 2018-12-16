package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Wind implements Parcelable {
    private String windDirectionDegrees;
    private String windDirectionCompass;
    private String hour;
    private double windSpeedMph;

    public Wind(String windDirectionDegrees, String windDirectionCompass, String hour,
                double windSpeedMph) {
        this.windDirectionDegrees = windDirectionDegrees;
        this.windDirectionCompass = windDirectionCompass;
        this.hour = hour;
        this.windSpeedMph = windSpeedMph;
    }

    protected Wind(Parcel in) {
        windDirectionDegrees = in.readString();
        windDirectionCompass = in.readString();
        hour = in.readString();
        windSpeedMph = in.readDouble();
    }

    public static final Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

    public double getWindSpeedMph() {
        return windSpeedMph;
    }

    public String getHour() {
        return hour;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public String getWindDirectionDegrees() {
        return windDirectionDegrees;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(windDirectionDegrees);
        dest.writeString(windDirectionCompass);
        dest.writeString(hour);
        dest.writeDouble(windSpeedMph);
    }
}
