package com.example.steven.drinkpicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.example.steven.drinkpicker.utils.FirebaseUtils;
import com.example.steven.drinkpicker.fragments.ImageSelectionFragment;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class AddDrinkToListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_list) Toolbar myToolbar;

    @BindView(R.id.location_container) RelativeLayout locationContainer;
    @BindView(R.id.image_container) RelativeLayout imageContainer;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.list_name_textInputEditText) TextInputEditText nameEditText;
    @BindView(R.id.list_alcohol_percentage_textInputEditText) TextInputEditText percentageEditText;

    private boolean isSaveButtonEnabled;
    private String name;
    private double percentage;
    private double rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink_list);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_white_24);
        setTitle(R.string.add_drink_list_activity);

        locationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectionFragment.newInstance(2).show(getSupportFragmentManager(), "imagefragment");
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (double) v;
            }
        });


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
        DrinkDiscovery drinkDiscovery = new DrinkDiscovery(name, percentage, rating);
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
            percentage = 0.0;
        } catch (NumberFormatException e) {
            percentage = 0.0;
        }
        setSaveButtonState();
    }

    private void setSaveButtonState() {
        if (name != null && percentage != 0.0 ) {
            isSaveButtonEnabled = true;
        } else {
            isSaveButtonEnabled = false;
        }
        invalidateOptionsMenu();
    }


}
