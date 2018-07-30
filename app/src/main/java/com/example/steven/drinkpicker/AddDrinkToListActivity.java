package com.example.steven.drinkpicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.steven.drinkpicker.firebasehelpers.FirebaseUtils;
import com.example.steven.drinkpicker.fragments.ImageSelectionFragment;
import com.example.steven.drinkpicker.objects.DrinkDiscovery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDrinkToListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_list) Toolbar myToolbar;

    @BindView(R.id.location_container) RelativeLayout locationContainer;
    @BindView(R.id.image_container) RelativeLayout imageContainer;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_drink_list, menu);
        //menu.findItem(R.id.list_menu_save).setEnabled(false);
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
        FirebaseUtils.addDrinkForUser(user,
                new DrinkDiscovery("Jupiler", 5.0, 5.0),
                "user1");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
    }
}
