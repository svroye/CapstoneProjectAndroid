package com.example.steven.drinkpicker.bachelpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtils {


    public static String getFormattedDate(long timeInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return formatter.format(calendar.getTime());
    }

    public static long getCurrentTimeInMillis(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
