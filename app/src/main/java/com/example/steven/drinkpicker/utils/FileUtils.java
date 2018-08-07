package com.example.steven.drinkpicker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.fragments.ImageSelectionFragment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {


    private static final String TAG = "FileUtils";

    //    public static final String TAG = "FileUtils";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
//    private static final String CAMERA_DIR = "/dcim/";
//
//    public static final String FILE_PROVIDER_AUTHORITY = "com.example.steven.drinkpicker";
//
//    public static File getAlbumDir(Context context) {
//        File storageDir = null;
//
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//
//            storageDir = new File(Environment.getExternalStorageDirectory()
//                    + CAMERA_DIR
//                    + context.getString(R.string.app_name));
//
//            Log.d(TAG, "Dir: " + storageDir);
//
//            if (storageDir != null) {
//                if (!storageDir.mkdirs()) {
//                    if (!storageDir.exists()) {
//                        Log.d(TAG, "failed to create directory");
//                        return null;
//                    }
//                }
//            }
//
//        } else {
//            Log.v(context.getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
//        }
//
//        return storageDir;
//    }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(null);
        File imageFile = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, storageDir);
        return imageFile;
    }

//    public static Bitmap getBitmapFromUri(Uri uri, Context context) {
//        ParcelFileDescriptor parcelFileDescriptor = null;
//        try {
//            parcelFileDescriptor =
//                    context.getContentResolver().openFileDescriptor(uri, "r");
//            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//            parcelFileDescriptor.close();
//            return image;
//        } catch (Exception e) {
//            Log.e(TAG, "Failed to load image.", e);
//            return null;
//        } finally {
//            try {
//                if (parcelFileDescriptor != null) {
//                    parcelFileDescriptor.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e(TAG, "Error closing ParcelFile Descriptor");
//            }
//        }
//    }


}
