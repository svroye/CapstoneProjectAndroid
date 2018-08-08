package com.example.steven.drinkpicker.objects;

public class DrinkDiscovery extends Drink {

    private double rating;
    private String placeId;
    private String photoUri;

    public DrinkDiscovery() {
    }

    public DrinkDiscovery(String name, double alcoholConcentration, double rating, String placeId,
                          String photoUri) {
        super(name, alcoholConcentration);
        this.rating = rating;
        this.placeId = placeId;
        this.photoUri = photoUri;
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

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String drinkID(){
        return getName() + "-" + (int) (getAlcoholConcentration()*10);
    }
}
