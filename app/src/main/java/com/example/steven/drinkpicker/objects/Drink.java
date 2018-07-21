package com.example.steven.drinkpicker.objects;

/**
 * Class representing a drink
 */
public class Drink {

    private String name;
    private double alcoholConcentration;

    public Drink(String name, double alcoholConcentration) {
        this.name = name;
        this.alcoholConcentration = alcoholConcentration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlcoholConcentration() {
        return alcoholConcentration;
    }

    public void setAlcoholConcentration(double alcoholConcentration) {
        this.alcoholConcentration = alcoholConcentration;
    }
}
