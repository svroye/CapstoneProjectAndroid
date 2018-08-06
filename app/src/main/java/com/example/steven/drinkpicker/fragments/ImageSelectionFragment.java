package com.example.steven.drinkpicker.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steven.drinkpicker.R;
import com.example.steven.drinkpicker.adapters.ImageSelectorAdapter;
import com.example.steven.drinkpicker.utils.FileUtils;
import com.example.steven.drinkpicker.utils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ImageSelectionFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ImageSelectionFragment extends BottomSheetDialogFragment
    implements ImageSelectorAdapter.OnListItemClickListener {

    private static final String LOG_TAG = "ImageSelectionFragment";

    private static final String ARG_ITEM_COUNT = "item_count";

    public OnItemSelectedListener mCallback;

    public interface OnItemSelectedListener {
        void onListItemSelectedListener(int position);
    }

    public static ImageSelectionFragment newInstance(int itemCount) {
        final ImageSelectionFragment fragment = new ImageSelectionFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ImageSelectorAdapter(getContext(),
                getArguments().getInt(ARG_ITEM_COUNT), this));
    }

    @Override
    public void onListItemClicked(int position) {
        mCallback.onListItemSelectedListener(position);
    }
            // "Take picture" option selected
//            ImageUtils.requestPermissions(getContext(), getActivity());
//            Intent takePictureIntent = ImageUtils.getIntentToTakePicture(getContext(), getActivity());
//            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case ImageUtils.MY_PERMISSIONS_REQUEST: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                    //mButtonTakePicture.setEnabled(true);
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
    }


}
