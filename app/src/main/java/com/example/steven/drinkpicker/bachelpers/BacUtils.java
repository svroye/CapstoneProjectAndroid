package com.example.steven.drinkpicker.bachelpers;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.database.DrinkBacContract;

public class BacUtils {

    public static final double R_FACTOR_MEN = 0.68;
    public static final double R_FACTOR_WOMEN = 0.55;

    public static final double BETA = 0.17;

    public static double calculateTotalBac(Context context){
        double r_factor = getRFactor(context);
        double weight = getWeight(context);

        Cursor result = context.getContentResolver().query(DrinkBacContract.DrinkBacEntry.CONTENT_URI,
                null, null, null, null);

        double totalBac = 0.0;

        while(result.moveToNext()) {
            double volume = result.getDouble(result.getColumnIndex(
                    DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_VOLUME));
            double percentage = result.getDouble(result.getColumnIndex(
                    DrinkBacContract.DrinkBacEntry.COLUMN_ALCOHOL_PERCENTAGE));
            long time = result.getLong(result.getColumnIndex(
                    DrinkBacContract.DrinkBacEntry.COLUMN_START_TIME));
            int id = result.getInt(result.getColumnIndex(
                    DrinkBacContract.DrinkBacEntry._ID));
            double singleBac = calculateSingleBac(r_factor, weight, volume, percentage, time);

            if (singleBac > 0) {
                totalBac += singleBac;
            } else {
                deleteDrinkEntry(context, id);
            }
        }
        return totalBac;
    }

    public static double calculateSingleBac(double rfactor, double weight,
                                            double volume, double percentage, long time) {
        long currentTime = DateTimeUtils.getCurrentTimeInMillis();
        double timeDifferenceInHours = (currentTime - time)/(1000.0*60.0*60.0);
        double bac = (volume * (percentage/100) * 8) / (rfactor * weight / (1.055))
                - timeDifferenceInHours * BETA;
        return bac;
    }

    private static void deleteDrinkEntry(Context context, int id) {
        Uri uri = ContentUris.withAppendedId(DrinkBacContract.DrinkBacEntry.CONTENT_URI, id);
        int del = context.getContentResolver().delete(uri, null, null);
        Log.d("BacUtils", "DEL: " + del);
    }

    private static double getWeight(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return ((double) sp.getFloat(context.getString(R.string.key_weight), 0.0F));
    }

    private static double getRFactor(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isMale = sp.getBoolean(context.getString(R.string.key_ismale), false);
        if (isMale) return R_FACTOR_MEN;
        return R_FACTOR_WOMEN;
    }

}
