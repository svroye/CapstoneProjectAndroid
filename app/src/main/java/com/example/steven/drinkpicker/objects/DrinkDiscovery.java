package com.example.steven.drinkpicker.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrinkDiscovery extends Drink {

    private double rating;
    private List<String> placeIds;
    private List<String> imageUris;

    public DrinkDiscovery() {
        this(null, 0.0, 0.0, null, null);
    }

    public DrinkDiscovery(Drink drink, double rating, ArrayList<String> placeIds, ArrayList<String> imageUris) {
        this(drink.getName(), drink.getAlcoholConcentration(), rating, placeIds, imageUris);
    }

    public DrinkDiscovery(String name, double alcoholConcentration, double rating, ArrayList<String> placeIds,
                          ArrayList<String> imageUris) {
        super(name, alcoholConcentration);
        this.rating = rating;

        if (null == placeIds) {
            this.placeIds = new ArrayList<>();
        } else {
            this.placeIds = placeIds;
        }

        if (null == imageUris) {
            this.imageUris = new ArrayList<>();
        } else {
            this.imageUris = imageUris;
        }

    }


    protected DrinkDiscovery(Parcel in) {
        super(in);
        rating = in.readDouble();
        placeIds = in.createStringArrayList();
        imageUris = in.createStringArrayList();
    }

    public static final Creator<DrinkDiscovery> CREATOR = new Creator<DrinkDiscovery>() {
        @Override
        public DrinkDiscovery createFromParcel(Parcel in) {
            return new DrinkDiscovery(in);
        }

        @Override
        public DrinkDiscovery[] newArray(int size) {
            return new DrinkDiscovery[size];
        }
    };

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getPlaceIds() {
        return placeIds;
    }

    public void setPlaceIds(ArrayList<String> placeIds) {
        this.placeIds = placeIds;
    }

    public List<String> getImageUris() {
        return imageUris;
    }

    public void setImageUris(ArrayList<String> imageUris) {
        this.imageUris = imageUris;
    }

    public void addImage(String imageUri) {
        this.imageUris.add(imageUri);
    }

    public void addPlace(String placeId) {
        this.placeIds.add(placeId);
    }

    @Override
    public String toString() {
        return "\nDrink:\t\t" + getName() +
                "\nAlcohol percentage:\t\t" + getAlcoholConcentration() +
                "\nRating\t\t" + this.rating +
                "\nPlaces:\t\t" + this.placeIds +
                "\nImageUris:\t\t" + this.imageUris;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeDouble(rating);
        parcel.writeStringList(placeIds);
        parcel.writeStringList(imageUris);
    }
}