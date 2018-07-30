package com.example.steven.drinkpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.steven.drinkpicker.firebasehelpers.FirebaseUtils;
import com.example.steven.drinkpicker.fragments.BloodAlcoholConcentrationFragment;
import com.example.steven.drinkpicker.fragments.DiscoveryFragment;
import com.example.steven.drinkpicker.fragments.MyDrinksFragment;
import com.example.steven.drinkpicker.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
            implements BloodAlcoholConcentrationFragment.OnFragmentInteractionListener,
        MyDrinksFragment.OnListFragmentInteractionListener{


    @BindView(R.id.my_toolbar) Toolbar myToolbar;
    @BindView(R.id.navigation) BottomNavigationView navigation;

    public static final int ADD_DRINK_BAC_REQUEST_CODE = 200;
    public static final int ADD_DRINK_LIST_REQUEST_CODE = 300;

    // click listener for the bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_discover:
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new DiscoveryFragment())
                            .commit();
                    return true;
                case R.id.navigation_my_drinks:
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new MyDrinksFragment())
                            .commit();
                    return true;
                case R.id.navigation_blood_alcohol_concentration:
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new BloodAlcoholConcentrationFragment())
                            .commit();
                    return true;
                case R.id.navigation_settings:
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new SettingsFragment())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // set the toolbar
        setSupportActionBar(myToolbar);

        // add listener to the bottom navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFabBacClicked() {
        Intent intent = new Intent(MainActivity.this, AddDrinkBACActivity.class);
        startActivityForResult(intent, ADD_DRINK_BAC_REQUEST_CODE);
        overridePendingTransition( R.anim.slide_bottom_to_top, R.anim.stay);
    }

    @Override
    public void onFabMyDrinksClicked() {
        Intent intent = new Intent(MainActivity.this, AddDrinkToListActivity.class);
        startActivityForResult(intent, ADD_DRINK_LIST_REQUEST_CODE);
        overridePendingTransition( R.anim.slide_bottom_to_top, R.anim.stay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case ADD_DRINK_BAC_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                }
        }
    }
}
