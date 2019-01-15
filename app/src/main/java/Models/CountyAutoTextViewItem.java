package Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CountyAutoTextViewItem implements Parcelable {
    private String countyName;
    private int imageId;

    public CountyAutoTextViewItem(String countyName, int imageId) {
        this.countyName = countyName;
        this.imageId = imageId;
    }

    protected CountyAutoTextViewItem(Parcel in) {
        countyName = in.readString();
        imageId = in.readInt();
    }

    public static final Creator<CountyAutoTextViewItem> CREATOR = new Creator<CountyAutoTextViewItem>() {
        @Override
        public CountyAutoTextViewItem createFromParcel(Parcel in) {
            return new CountyAutoTextViewItem(in);
        }

        @Override
        public CountyAutoTextViewItem[] newArray(int size) {
            return new CountyAutoTextViewItem[size];
        }
    };

    public int getImageId() {
        return imageId;
    }

    public String getCountyName() {
        return countyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(countyName);
        parcel.writeInt(imageId);
    }
}
