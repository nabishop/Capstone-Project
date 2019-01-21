package ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ContentProvider.Contract.BeachEntry.TABLE_NAME;
import static ContentProvider.Contract.BeachEntry.COLUMN_BEACH_NAME;
import static ContentProvider.Contract.BeachEntry.COLUMN_BEACH_ID;
import static ContentProvider.Contract.BeachEntry.COLUMN_BEACH_COUNTY;

import static android.provider.BaseColumns._ID;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "beachList.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BEACH_NAME + " TEXT NOT NULL, " + COLUMN_BEACH_ID + " TEXT , "
                + COLUMN_BEACH_COUNTY + " TEXT NOT NULL "+");";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
