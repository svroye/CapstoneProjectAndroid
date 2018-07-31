package com.example.steven.drinkpicker.utils;

import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static void addDrinkForUser(String user,DrinkDiscovery drink){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("users").child(user).child(drink.drinkID()).setValue(drink);
        //db.child("drinks").child(drink.getDrinkId()).setValue(drink);
    }

}
