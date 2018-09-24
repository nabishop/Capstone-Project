package ContentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {
    public static final String CONTENT_AUTHORITY = "com.example.android.rightide";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BEACH = "beach";

    public static final class BeachEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_BEACH).build();

        public static final String TABLE_NAME = "beachTable";
        public static final String COLUMN_BEACH_NAME = "beachName";
        public static final String COLUMN_BEACH_ID = "beachId";
        public static final String COLUMN_BEACH_COUNTY = "beachCounty";
        public static final String COLUMN_BEACH_PICTURE = "beachPicture";
    }
}
