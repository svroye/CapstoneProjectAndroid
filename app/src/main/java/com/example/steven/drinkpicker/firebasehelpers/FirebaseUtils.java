package com.example.steven.drinkpicker.firebasehelpers;

import android.util.Log;

import com.example.steven.drinkpicker.objects.Drink;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static void addDrinkForUser(String user,DrinkDiscovery drink){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("users").child(user).setValue(drink.getDrinkId());
        db.child("drinks").child(drink.getDrinkId()).setValue(drink);
    }

}
