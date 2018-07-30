package com.example.steven.drinkpicker.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.steven.drinkpicker.LoginActivity;
import com.example.steven.drinkpicker.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment {

    @BindView(R.id.gender_group) RadioGroup genderGroup;
    @BindView(R.id.radiobutton_male) RadioButton radioMale;
    @BindView(R.id.radiobutton_female) RadioButton radioFemale;
    @BindView(R.id.weight_input) EditText weightEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setRadioButton();
        setEditText();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sp.edit();
                boolean isMale;
                if (i == R.id.radiobutton_male) {
                    isMale = true;
                } else {
                    isMale = false;
                }
                editor.putBoolean(getString(R.string.key_ismale), isMale);
                editor.commit();
            }
        });

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sp.edit();
                String weightString = weightEditText.getText().toString().trim();
                float weight;

                try {
                    weight = Float.parseFloat(weightString);
                } catch (NullPointerException e) {
                    weight = 0.0F;
                } catch (NumberFormatException e) {
                    weight = 0.0F;
                }
                editor.putFloat(getString(R.string.key_weight), weight);
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void setEditText() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        float weight = sp.getFloat(getString(R.string.key_weight), 0.0F);
        weightEditText.setText(String.valueOf(weight));
    }

    private void setRadioButton() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isMale = sp.getBoolean(getString(R.string.key_ismale), false);
        radioMale.setChecked(isMale);
        radioFemale.setChecked(!isMale);
    }

    @OnClick(R.id.log_out)
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }


}
