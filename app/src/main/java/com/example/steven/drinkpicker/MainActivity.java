package com.example.steven.drinkpicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.steven.drinkpicker.fragments.BloodAlcoholConcentrationFragment;
import com.example.steven.drinkpicker.fragments.DiscoveryFragment;
import com.example.steven.drinkpicker.fragments.MyDrinksFragment;

public class MainActivity extends AppCompatActivity{

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
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // set the bottom navigation and add a listener
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
