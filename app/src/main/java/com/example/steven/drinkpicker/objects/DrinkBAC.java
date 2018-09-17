package com.example.steven.drinkpicker.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for representing drinks used in the "Blood Alcohol Concentration"
 */
public class DrinkBAC extends Drink {

    private double volume;
    private long startTime;

    public DrinkBAC(String name, double alcoholPercentage, double volume, long startTime) {
        super(name, alcoholPercentage);
        this.volume = volume;
        this.startTime = startTime;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    protected DrinkBAC(Parcel in) {
        super(in);
        volume = in.readDouble();
        startTime = in.readLong();
    }

    public static final Creator<DrinkBAC> CREATOR = new Creator<DrinkBAC>() {
        @Override
        public DrinkBAC createFromParcel(Parcel in) {
            return new DrinkBAC(in);
        }

        @Override
        public DrinkBAC[] newArray(int size) {
            return new DrinkBAC[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(volume);
        parcel.writeLong(startTime);
    }
}
