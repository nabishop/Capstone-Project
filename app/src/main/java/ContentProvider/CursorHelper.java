package ContentProvider;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Models.Beach;

public class CursorHelper {

    public static ArrayList<Integer> getFavoritedBeacheIds(Context context) {
        int spotId;
        String[] columnsToRetrieve = {Contract.BeachEntry.COLUMN_BEACH_ID};

        Cursor cursor = context.getContentResolver().query(Contract.BeachEntry.CONTENT_URI,
                columnsToRetrieve, null, null, null);

        if (cursor.moveToFirst()) {
            ArrayList<Integer> beachList = new ArrayList<>();
            spotId = cursor.getInt(cursor.getColumnIndex(Contract.BeachEntry.COLUMN_BEACH_ID));
            beachList.add(spotId);
            while (cursor.moveToNext()) {
                spotId = cursor.getInt(cursor.getColumnIndex(Contract.BeachEntry.COLUMN_BEACH_ID));
                beachList.add(spotId);
            }
            return beachList;
        } else {
            return null;
        }
    }
}
