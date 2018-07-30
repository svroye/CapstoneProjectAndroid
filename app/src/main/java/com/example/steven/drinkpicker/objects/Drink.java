package com.example.steven.drinkpicker.objects;

/**
 * Class representing a drink
 */
public class Drink {

    private String name;
    private double alcoholPercentage;

    public Drink() {
    }

    public Drink(String name, double alcoholPercentage) {
        this.name = name;
        this.alcoholPercentage = alcoholPercentage;
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
}
