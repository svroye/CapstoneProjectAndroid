package com.example.steven.drinkpicker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrinkBacDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "drinkbac.db";
    static final int DATABASE_VERSION = 1;

    public DrinkBacDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_DRINK_BAC_TABLE = "CREATE TABLE " +
                DrinkBacContract.DrinkBacEntry.TABLE_NAME + "(" +
                DrinkBacContract.DrinkBacEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_NAME + " TEXT NOT NULL, " +
                DrinkBacContract.DrinkBacEntry.COLUMN_ALCOHOL_PERCENTAGE + " REAL NOT NULL, " +
                DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_VOLUME + " REAL NOT NULL, " +
                DrinkBacContract.DrinkBacEntry.COLUMN_START_TIME + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_DRINK_BAC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // nothing for the moment
    }
}
