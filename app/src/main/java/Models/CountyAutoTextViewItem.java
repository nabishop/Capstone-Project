package Models;

public class CountyAutoTextViewItem {
    private String countyName;
    private int imageId;

    public CountyAutoTextViewItem(String countyName, int imageId) {
        this.countyName = countyName;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getCountyName() {
        return countyName;
    }
}
