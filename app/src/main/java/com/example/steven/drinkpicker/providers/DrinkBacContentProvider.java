package com.example.steven.drinkpicker.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.steven.drinkpicker.database.DrinkBacContract;
import com.example.steven.drinkpicker.database.DrinkBacDBHelper;

import java.sql.SQLInput;

public class DrinkBacContentProvider extends ContentProvider {

    DrinkBacDBHelper helper;

    public static final int DRINKS = 100;
    public static final int DRINK_WITH_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(DrinkBacContract.AUTHORITHY, DrinkBacContract.PATH_DRINKS, DRINKS);
        matcher.addURI(DrinkBacContract.AUTHORITHY, DrinkBacContract.PATH_DRINKS + "/#",
                DRINK_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        helper = new DrinkBacDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase mDb = helper.getReadableDatabase();
        int matcher = uriMatcher.match(uri);

        Cursor returnCursor;

        switch (matcher) {
            case DRINKS:
                returnCursor = mDb.query(DrinkBacContract.DrinkBacEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case DRINK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[] {id};
                returnCursor = mDb.query(DrinkBacContract.DrinkBacEntry.TABLE_NAME, projection,
                        mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase mDb = helper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case DRINKS:
                long id = mDb.insert(DrinkBacContract.DrinkBacEntry.TABLE_NAME,
                        null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(
                            DrinkBacContract.DrinkBacEntry.CONTENT_URI, id);
                } else {
                    throw new SQLiteException("Failed to insert row into " + uri);
                }
                break;
            case DRINK_WITH_ID:
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase mDb = helper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        int rowsDeleted = 0;

        switch (match) {
            case DRINKS:
                break;
            case DRINK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                rowsDeleted = mDb.delete(DrinkBacContract.DrinkBacEntry.TABLE_NAME,
                        "_id=?", new String[] {id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase mDb = helper.getWritableDatabase();
        int match =uriMatcher.match(uri);

        int rowsUpdated = 0;

        switch(match) {
            case DRINKS:
                break;
            case DRINK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                rowsUpdated = mDb.update(DrinkBacContract.DrinkBacEntry.TABLE_NAME, contentValues,
                        "_id=?", new String[] {id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
