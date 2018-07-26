package com.example.steven.drinkpicker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.steven.drinkpicker.bachelpers.DateTimeUtils;
import com.example.steven.drinkpicker.database.DrinkBacContract;
import com.example.steven.drinkpicker.fragments.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddDrinkBACActivity extends AppCompatActivity
    implements TimePickerFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar_bac)  Toolbar toolbar;
    @BindView(R.id.bac_name_textInputEditText) TextInputEditText nameEditText;
    @BindView(R.id.bac_alcohol_percentage_textInputEditText) TextInputEditText alcoholEditText;
    @BindView(R.id.bac_volume_textInputEditText) TextInputEditText volumeEditText;
    @BindView(R.id.bac_time_textInputEditText) TextInputEditText timeEditText;

    private boolean isSaveButtonEnabled;
    private String name;
    private double percentage;
    private double volume;
    private String time;
    private long timeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink_bac);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_white_24);
        setTitle(R.string.add_drink_bac_activity);
        Calendar c = Calendar.getInstance();


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED ,returnIntent);
            finish();
            overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
        } else if (itemId == R.id.bac_menu_save) {
            saveDrink();
            finish();
            overridePendingTransition(R.anim.stay, R.anim.slide_top_to_bottom);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_drink_bac, menu);
        menu.findItem(R.id.bac_menu_save).setEnabled(isSaveButtonEnabled);
        return true;
    }


    @OnClick({R.id.bac_time_textInputLayout, R.id.bac_time_textInputEditText})
    void onTimeButtonClicked(){
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSelected(TimePicker timepicker, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        // get current year for comparison
        long currentTime = calendar.getTimeInMillis();

        // set the calendar based on the picked time and the date from the current day
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), hour, min);

        // get the time entered by the user from the calendar in ms
        timeInMillis = calendar.getTimeInMillis();

        // if the start time is greater than the current time, the drink was consumed yesterday and
        // we have to subtract 1 day from the instance
        if (timeInMillis > currentTime) {
            calendar.add(Calendar.DATE, -1);
            timeInMillis = calendar.getTimeInMillis();
        }
        time = DateTimeUtils.getFormattedDate(timeInMillis);
        timeEditText.setText(time);
        setSaveButtonState();
    }

    @OnTextChanged(R.id.bac_name_textInputEditText)
    void nameChangeListener() {
        name = nameEditText.getText().toString().trim();
        setSaveButtonState();
    }

    @OnTextChanged(R.id.bac_alcohol_percentage_textInputEditText)
    void percentageChangeListener() {
        try {
            percentage = Double.parseDouble(alcoholEditText.getText().toString().trim());
        } catch (NullPointerException e) {
            percentage = 0.0;
        } catch (NumberFormatException e) {
            percentage = 0.0;
        }
        setSaveButtonState();
    }

    @OnTextChanged(R.id.bac_volume_textInputEditText)
    void volumeChangeListener() {
        try {
            volume = Double.parseDouble(volumeEditText.getText().toString().trim());
        } catch (NullPointerException e) {
            percentage = 0.0;
        } catch (NumberFormatException e) {
            percentage = 0.0;
        }
        setSaveButtonState();
    }

    void setSaveButtonState(){
        Log.d("ADDDRINKBAC", name + "\t" + percentage + "\t" + volume + "\t" + time);
        if (name != null && percentage != 0.0 && volume != 0.0 && time != null) {
            isSaveButtonEnabled = true;
        } else {
            isSaveButtonEnabled = false;
        }
        invalidateOptionsMenu();
    }

    void saveDrink(){
        ContentResolver  resolver = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_NAME, name);
        cv.put(DrinkBacContract.DrinkBacEntry.COLUMN_ALCOHOL_PERCENTAGE, percentage);
        cv.put(DrinkBacContract.DrinkBacEntry.COLUMN_DRINK_VOLUME, volume);
        cv.put(DrinkBacContract.DrinkBacEntry.COLUMN_START_TIME, timeInMillis);
        Uri returnUri = resolver.insert(DrinkBacContract.DrinkBacEntry.CONTENT_URI, cv);
        if (returnUri != null) {
            Toast.makeText(this, "" + returnUri, Toast.LENGTH_SHORT).show();
        }
    }

}
