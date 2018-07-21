package com.example.steven.drinkpicker.objects;

/**
 * Class for representing drinks used in the "Blood Alcohol Concentration"
 */
public class DrinkBAC extends Drink {

    private double volume;
    private long startTime;

    public DrinkBAC(String name, double alcoholConcentration, double volume, long startTime) {
        super(name, alcoholConcentration);
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
}
