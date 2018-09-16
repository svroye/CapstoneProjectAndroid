package com.example.steven.drinkpicker.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.objects.Drink;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;

public class FirebaseUtils {

    private static final String LOG_TAG = "FirebaseUtils";

    public static final String CHILD_USERS = "users";
    public static final String CHILD_DRINKS = "drinks";
    public static final String CHILD_LOCATIONS = "locations";
    public static final String CHILD_IMAGE_URIS = "imageUris";
    public static final String CHILD_PLACE_IDS = "placeIds";

    // get a reference to the Firebase Realtime Database
    public static DatabaseReference getDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference();
    }

    // get a reference to the Firebase Storage
    public static StorageReference getStorageReference(){
        return FirebaseStorage.getInstance().getReference();
    }

    /**
     * Adds a drink to the Firebase Realtime database and Storage
     * The image is first uploaded; upon successful upload, the drink is added to the DB in 
     * several steps
     * @param userId: user for which the add the drink
     * @param drinkDiscovery: information of the drink to add
     * @param localImageUri: image to be uploaded, stored locally on the phone
     */
    public static void addDrink(final Context context, final String userId, 
                                final DrinkDiscovery drinkDiscovery, Uri localImageUri) {
        // get a reference for the storage of the image
        StorageReference storageReference = getStorageReference();
        Date date = new Date();
        final StorageReference drinkRef = storageReference.child(userId + "/" + drinkDiscovery.drinkID() + "/" + drinkDiscovery.getPlaceIds().get(0) + "/" + date.getTime() + ".jpg");
        // upload the image
        drinkRef.putFile(localImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // when upload is successful, set the download URI to the DrinkDiscovery instance
                        // and add the drink to the DB
                        drinkRef.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d(LOG_TAG, "Upload successful. URI: " + uri);
                                        drinkDiscovery.addImage(uri.toString());
                                        addDrinkInfo(context, userId, drinkDiscovery);
                                        addLocationInfo(drinkDiscovery);
                                        addUserInfo(userId, drinkDiscovery, context);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LOG_TAG, "Upload failed: " + e);
                    }
                });
    }

    // Add a drink to the database. Creates a new entry if it does not exist yet.
    // Stores a reference to the locations of the drink, as well as to the users who added it.
    public static void addDrinkInfo(Context context, String userId, DrinkDiscovery drinkDiscovery) {
        DatabaseReference db = getDatabaseReference();

        // create map with data to push to Firebase Database
        HashMap<String, Object> map = new HashMap<>();
        map.put(context.getString(R.string.key_name), drinkDiscovery.getName());
        map.put(context.getString(R.string.key_alcohol_percentage), drinkDiscovery.getAlcoholConcentration());

        // push the result in the drinks => drinkId node
        db.child(CHILD_DRINKS).child(drinkDiscovery.drinkID()).updateChildren(map);
        // add the user to the drink to keep a reference which users have added this drink
        db.child(CHILD_DRINKS).child(drinkDiscovery.drinkID()).child(CHILD_USERS).child(userId).setValue(true);
        // add the location to the drink to keep a reference to the location where this drink can be ordered
        db.child(CHILD_DRINKS).child(drinkDiscovery.drinkID()).child(CHILD_LOCATIONS)
                .child(drinkDiscovery.getPlaceIds().get(0)).setValue(true);
    }

    // adds the drink to the location
    public static void addLocationInfo(DrinkDiscovery drinkDiscovery) {
        DatabaseReference db = getDatabaseReference();

        db.child(CHILD_LOCATIONS).child(drinkDiscovery.getPlaceIds().get(0)).child(CHILD_DRINKS)
                .child(drinkDiscovery.drinkID()).setValue(true);
    }

    // adds the drink to the user
    public static void addUserInfo(String userId, DrinkDiscovery drinkDiscovery, Context context){
        DatabaseReference db = getDatabaseReference();

        HashMap<String, Object> map = new HashMap<>();
        map.put(context.getString(R.string.key_name), drinkDiscovery.getName());
        map.put(context.getString(R.string.key_alcohol_percentage), drinkDiscovery.getAlcoholConcentration());
        map.put(context.getString(R.string.key_rating), drinkDiscovery.getRating());

        db.child(CHILD_USERS).child(userId).child(drinkDiscovery.drinkID()).updateChildren(map);
        Date date = new Date();
        db.child(CHILD_USERS).child(userId).child(drinkDiscovery.drinkID()).child(CHILD_PLACE_IDS).child(String.valueOf(date.getTime())).setValue(drinkDiscovery.getPlaceIds().get(0));
        db.child(CHILD_USERS).child(userId).child(drinkDiscovery.drinkID()).child(CHILD_IMAGE_URIS).child(String.valueOf(date.getTime())).setValue(drinkDiscovery.getImageUris().get(0));
    }

    public static DrinkDiscovery initializeDrinkItem(DataSnapshot snapshot, Context context){

        DrinkDiscovery drinkDiscovery = new DrinkDiscovery();

        drinkDiscovery.setName(snapshot.child(context.getString(R.string.key_name)).getValue()
                .toString());
        drinkDiscovery.setAlcoholConcentration(Double.parseDouble(snapshot.child(
                context.getString(R.string.key_alcohol_percentage)).getValue().toString()));
        drinkDiscovery.setRating(Double.parseDouble(snapshot.child(context.getString(
                R.string.key_rating)).getValue().toString()));

        drinkDiscovery.setName(snapshot.child(context.getString(R.string.key_name)).getValue()
                .toString());
        drinkDiscovery.setName(snapshot.child(context.getString(R.string.key_name)).getValue()
                .toString());

        for (DataSnapshot imageUrisSnapshot : snapshot.child(context.getString(
                R.string.key_imageUris)).getChildren()) {
            drinkDiscovery.addImage(imageUrisSnapshot.getValue().toString());
        }

        for (DataSnapshot placeIdSnapshot: snapshot.child(context.getString(
                R.string.key_placeIds)).getChildren()) {
            drinkDiscovery.addPlace(placeIdSnapshot.getValue().toString());
        }
        return drinkDiscovery;
    }

}
