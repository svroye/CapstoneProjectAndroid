package com.example.steven.drinkpicker.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageUtils {


    private static final String TAG = "ImageUtils";

    public static final int REQUEST_IMAGE_GET = 1;

//    public static final int MY_PERMISSIONS_REQUEST = 3;


    public static Intent getIntentToOpenGallery(){
        Intent intentToOpenGallery;
        if (Build.VERSION.SDK_INT < 19) {
            Log.d(TAG, "SDK_INT < 19");
            intentToOpenGallery = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            Log.d(TAG, "SDK_INT >= 19");
            intentToOpenGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intentToOpenGallery.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intentToOpenGallery.setType("image/*");
        return intentToOpenGallery;
    }





//    public static void requestPermissions(Context context, Activity activity) {
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED ) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        } else {
//            //mButtonTakePicture.setEnabled(true);
//        }
//    }
//
//    public static Intent getIntentToTakePicture(Context context, Activity activity){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        try {
//            File f = FileUtils.createImageFile(context);
//
//            Uri mImageFromGalleryOrCamera = FileProvider.getUriForFile(context,
//                    FileUtils.FILE_PROVIDER_AUTHORITY, f);
//
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageFromGalleryOrCamera);
//
//            // Solution taken from http://stackoverflow.com/a/18332000/3346625
//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
//                List<ResolveInfo> resInfoList = activity.getPackageManager()
//                        .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolveInfo : resInfoList) {
//                    String packageName = resolveInfo.activityInfo.packageName;
//                    activity.grantUriPermission(packageName, mImageFromGalleryOrCamera,
//                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
//                                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }
//            }
//
//            return takePictureIntent;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//


}
