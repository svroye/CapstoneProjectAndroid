package com.example.steven.drinkpicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.steven.drinkpicker.utils.FileUtils;
import com.example.steven.drinkpicker.utils.FirebaseUtils;
import com.example.steven.drinkpicker.fragments.ImageSelectionFragment;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.example.steven.drinkpicker.utils.ImageUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddDrinkToListActivity extends AppCompatActivity 
        implements ImageSelectionFragment.OnItemSelectedListener{

    private static final String LOG_TAG = "AddDrinkToListActivity";
    @BindView(R.id.toolbar_list) Toolbar myToolbar;
    private int PLACE_PICKER_REQUEST = 3;

    @BindView(R.id.list_location_textInputEditText) TextInputEditText locationEditText;
    @BindView(R.id.image_container) RelativeLayout imageContainer;
    @BindView(R.id.drink_image) ImageView drinkImageView;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.list_name_textInputEditText) TextInputEditText nameEditText;
    @BindView(R.id.list_alcohol_percentage_textInputEditText) TextInputEditText percentageEditText;

    private ImageSelectionFragment imageSelectionFragment;
    private boolean isSaveButtonEnabled;


    private String name;
    private double percentage;
    private String locationId;
    private double rating;
    private Uri cameraPictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink_list);
        ButterKnife.bind(this);
        percentage = -1.0;
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_white_24);
        setTitle(R.string.add_drink_list_activity);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (double) v;
            }
        });
    }

    @OnClick({R.id.list_location_textInputLayout, R.id.list_location_textInputEditText})
    void onSelectLocationClicked(){
        Log.d(LOG_TAG, "Inside onSelectLocationClicked");
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build((Activity) (AddDrinkToListActivity.this)), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.image_container)
    void onImageContainerClicked(){
        Log.d(LOG_TAG, "Inside onImageContainerClicked");
        imageSelectionFragment = ImageSelectionFragment.newInstance(2);
        imageSelectionFragment.show(getSupportFragmentManager(), "imagefragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_drink_list, menu);
        menu.findItem(R.id.list_menu_save).setEnabled(isSaveButtonEnabled);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED ,returnIntent);
            finish();
            overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
        } else if (itemId == R.id.list_menu_save) {
            saveDrinkEntry();
            finish();
            overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
        }
        return true;
    }

    private void saveDrinkEntry() {
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DrinkDiscovery drinkDiscovery = new DrinkDiscovery(name, percentage, rating,
                locationId, cameraPictureUri.toString());
        FirebaseUtils.addDrinkForUser(user, drinkDiscovery);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
    }

    @OnTextChanged(R.id.list_name_textInputEditText)
    void onNameChanged(){
        name = nameEditText.getText().toString().trim();
        setSaveButtonState();
    }

    @OnTextChanged(R.id.list_alcohol_percentage_textInputEditText)
    void onPercentageChanged(){
        try {
            percentage = Double.parseDouble(percentageEditText.getText().toString().trim());
        } catch (NullPointerException e) {
            percentage = -1.0;
        } catch (NumberFormatException e) {
            percentage = -1.0;
        }
        setSaveButtonState();
    }

    private void setSaveButtonState() {
        Log.d(LOG_TAG, "" + (name != null) + " " + (name.length() > 0) + " " + (percentage != -1.0)
                + " " + (locationId != null) + " " + " " + (cameraPictureUri != null));
        if (name != null && name.length() > 0 && percentage != -1.0 &&
                locationId != null && cameraPictureUri != null) {
            isSaveButtonEnabled = true;
        } else {
            isSaveButtonEnabled = false;
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onListItemSelectedListener(int position) {
        if (position == 0) {
            // "Take picture" option selected
            Intent intent = ImageUtils.getIntentToCamera(this);
            cameraPictureUri= (Uri) intent.getExtras().get(MediaStore.EXTRA_OUTPUT);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, ImageUtils.REQUEST_IMAGE_CAPTURE);
            }
        } else {
            // "Select image from gallery" option selected
            // get intent and start it
            Intent intent = ImageUtils.getIntentToOpenGallery();
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent,
                        ImageUtils.REQUEST_IMAGE_GET);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // image picker/camera intent was successful
            if (requestCode == ImageUtils.REQUEST_IMAGE_GET) {
                // option for the image picker from the gallery
                // get uri to the selected picture and set it to the image view
                cameraPictureUri = data.getData();
                Picasso.get()
                        .load(cameraPictureUri)
                        .fit()
                        .into(drinkImageView);
            } else if (requestCode == ImageUtils.REQUEST_IMAGE_CAPTURE) {
                Picasso.get()
                        .load(cameraPictureUri)
                        .fit()
                        .into(drinkImageView);
            } else if (requestCode == PLACE_PICKER_REQUEST) {
                Place place = PlacePicker.getPlace(this, data);
                locationId = place.getId();
                locationEditText.setText(place.getName() + "\n" + place.getAddress());
            }
        }
        setSaveButtonState();
    }
}
