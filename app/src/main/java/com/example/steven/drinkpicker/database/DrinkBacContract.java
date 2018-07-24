package com.example.steven.drinkpicker.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DrinkBacContract {

    // constant for the content provider
    public static final String SCHEME = "content://";
    public static final String AUTHORITHY = "com.example.steven.drinkpicker";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITHY);

    public static final String PATH_DRINKS = "drinksbac";

    public static final class DrinkBacEntry implements BaseColumns {

        // URI for accessing data of this table through a content provider
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_DRINKS)
                .build();

        // table name and column names
        public static final String TABLE_NAME = "drinksbac";
        public static final String COLUMN_DRINK_NAME = "name";
        public static final String COLUMN_ALCOHOL_PERCENTAGE = "alcoholPercentage";
        public static final String COLUMN_DRINK_VOLUME = "volume";
        public static final String COLUMN_START_TIME = "startTime";
    }
}
