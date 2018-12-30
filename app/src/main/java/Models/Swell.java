package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Swell implements Parcelable {

    protected Swell(Parcel in) {
    }

    public static final Creator<Swell> CREATOR = new Creator<Swell>() {
        @Override
        public Swell createFromParcel(Parcel in) {
            return new Swell(in);
        }

        @Override
        public Swell[] newArray(int size) {
            return new Swell[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
