package com.example.steven.drinkpicker.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a drink
 */
public abstract class Drink implements Parcelable {

    private String name;
    private double alcoholPercentage;

    public Drink() {
    }

    public Drink(String name, double alcoholPercentage) {
        this.name = name;
        this.alcoholPercentage = alcoholPercentage;
    }


    protected Drink(Parcel in) {
        name = in.readString();
        alcoholPercentage = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlcoholConcentration() {
        return alcoholPercentage;
    }

    public void setAlcoholConcentration(double alcoholConcentration) {
        this.alcoholPercentage = alcoholConcentration;
    }

    public String drinkID() {
        return name + "-" + (int) (alcoholPercentage * 10);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(alcoholPercentage);
    }
}