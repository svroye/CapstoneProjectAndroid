package com.example.steven.drinkpicker;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.steven.drinkpicker.objects.DrinkDiscovery;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDrinkDetailActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MyDrinkDetailActivity";

    @BindView(R.id.toolbar_drinkDetail) Toolbar toolbar;
    @BindView(R.id.detail_name) TextView nameTv;
    @BindView(R.id.detail_ratingBar) RatingBar ratingBar;
    @BindView(R.id.detail_percentage) TextView percentageTv;
    @BindView(R.id.detail_recyclerview_images) RecyclerView imagesRv;
    @BindView(R.id.detail_recyclerview_places) RecyclerView placesRv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drink_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button_white);

        Intent intent = getIntent();
        if( null == intent) finish();

        DrinkDiscovery drinkDiscovery =  intent.getParcelableExtra(getString(R.string.key_drink_discovery));
        Log.d(LOG_TAG, drinkDiscovery.toString());
        setTitle(drinkDiscovery.getName());
        initializeViews(drinkDiscovery);

    }

    public void initializeViews(DrinkDiscovery drinkDiscovery){
        nameTv.setText(drinkDiscovery.getName());
        ratingBar.setIsIndicator(true);

        ratingBar.setRating((float) drinkDiscovery.getRating());
        percentageTv.setText(getString(R.string.percentage_formatter,drinkDiscovery.getAlcoholConcentration()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
