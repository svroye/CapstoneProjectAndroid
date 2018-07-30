package com.example.steven.drinkpicker.objects;

public class DrinkDiscovery extends Drink {

    private double rating;

    public DrinkDiscovery() {
    }

    public DrinkDiscovery(String name, double alcoholConcentration, double rating) {
        super(name, alcoholConcentration);
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDrinkId(){
        return getName() + "-" + (int) (getAlcoholConcentration()*10) + "-" + (int) (getRating()*10);
    }
}
