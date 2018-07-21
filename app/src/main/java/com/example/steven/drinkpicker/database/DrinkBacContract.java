package com.example.steven.drinkpicker.database;

import android.provider.BaseColumns;

public class DrinkBacContract {

    public static final class DrinkBacEntry implements BaseColumns {
        static final String TABLE_NAME = "drinks-bac";
        static final String COLUMN_DRINK_NAME = "name";
        static final String COLUMN_ALCOHOL_PERCENTAGE = "alcoholPercentage";
        static final String COLUMN_DRINK_VOLUME = "volume";
        static final String COLUMN_START_TIME = "startTime";
    }
}
