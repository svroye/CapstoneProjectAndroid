package com.example.steven.drinkpicker;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.steven.drinkpicker.fragments.TimePickerFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddDrinkBACActivity extends AppCompatActivity
    implements TimePickerFragment.OnFragmentInteractionListener{

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
        int hour = c.get(Calendar.HOUR);
        int min = c.get(Calendar.MINUTE);
        time = getString(R.string.time_format_string, hour, min);

        timeEditText.setText(time);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
        } else if (itemId == R.id.bac_menu_save) {

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
        time = getString(R.string.time_format_string, hour, min);
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
        Log.d("AddBacDrink", name + "\t" + percentage + "\t" + volume + "\t" + time );
        if (name != null && percentage != 0.0 && volume != 0.0 && time != null) {
            isSaveButtonEnabled = true;
        } else {
            isSaveButtonEnabled = false;
        }
        invalidateOptionsMenu();
    }
}
