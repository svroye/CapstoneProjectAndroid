package com.example.steven.drinkpicker.objects;

public class DrinkDiscovery extends Drink {

    private double rating;
    private String placeId;
    private String imageUri;

    public DrinkDiscovery() {
    }

    public DrinkDiscovery(Drink drink, double rating, String placeId, String imageUri) {
        this(drink.getName(), drink.getAlcoholConcentration(), rating, placeId, imageUri);
    }

    public DrinkDiscovery(String name, double alcoholConcentration, double rating, String placeId,
                          String imageUri) {
        super(name, alcoholConcentration);
        this.rating = rating;
        this.placeId = placeId;
        this.imageUri = imageUri;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
