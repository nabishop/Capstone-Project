package ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ContentProvider.Contract.BeachEntry.CONTENT_URI;
import static ContentProvider.Contract.BeachEntry.TABLE_NAME;

public class BeachContentProvider extends ContentProvider {
    private DbHelper mDbHelper;
    private static final int BEACH = 100;
    private static final int BEACH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_BEACH, BEACH);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_BEACH + "/#", BEACH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);
        return true;
    }

    public boolean checkIfFavoritesExist() {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"*"},
                null, null, null, null, null, null);

        if(cursor==null || !cursor.moveToFirst())
            return false;

        cursor.close();
        db.close();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {

            case BEACH:
                retCursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BEACH_ID:
                String id = uri.getLastPathSegment();
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};
                retCursor = db.query(TABLE_NAME, projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        db.close();
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("getType() not implemented yet");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match) {
            case BEACH:
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int retInt;

        switch (match) {
            case BEACH:
                retInt = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case BEACH_ID:
                String movieId = uri.getLastPathSegment();
                retInt = db.delete(TABLE_NAME, "_id=?",
                        new String[]{movieId});
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        if (selection == null || retInt != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        db.close();
        return retInt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int retInt;

        switch (match) {
            case BEACH_ID:
                String movieId = uri.getLastPathSegment();
                retInt = db.update(TABLE_NAME, values,
                        "_id=?", new String[]{movieId});
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return retInt;
    }
}
