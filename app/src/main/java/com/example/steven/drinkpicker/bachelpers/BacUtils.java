package com.example.steven.drinkpicker.bachelpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.database.DrinkBacContract;

public class BacUtils {

    public static final double R_FACTOR_MEN = 0.68;
    public static final double R_FACTOR_WOMEN = 0.55;

    public static final double BETA = 0.17;

    public static void calculateBac(Context context){
        double r_factor = getRFactor(context);
        double weight = getWeight(context);
        double alcoholConsumption = calculateAlcoholConsumption(context);
        //double bac = (n * V * p * 8) / (r_factor * weight / (1.055)) - t * BETA;
        Log.d("BACUTILS", "R: " + r_factor);
        Log.d("BACUTILS", "weight: " + weight);
        Log.d("BACUTILS", "consumption: " + alcoholConsumption);
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

    private static double calculateAlcoholConsumption(Context context){
        Cursor result = context.getContentResolver().query(DrinkBacContract.DrinkBacEntry.CONTENT_URI,
                null, null, null, null);
        double alcoholConsumption = 0.0;
        while(result.moveToNext()){
            double volume = result.getDouble(result.getColumnIndex(
                    DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_VOLUME));
            double percentage = result.getDouble(result.getColumnIndex(
                    DrinkBacContract.DrinkBacEntry.COLUMN_ALCOHOL_PERCENTAGE));
            alcoholConsumption += (volume * percentage);
        }
        return alcoholConsumption;
    }


}
