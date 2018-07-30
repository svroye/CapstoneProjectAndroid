package com.example.steven.drinkpicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.steven.drinkpicker.firebasehelpers.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.email) TextInputEditText mEmailView;
    @BindView(R.id.password) TextInputEditText mPasswordView;
    @BindView(R.id.email_inputlayout) TextInputLayout mEmailInputLayout;
    @BindView(R.id.password_inputlayout) TextInputLayout mPasswordInputLayout;

    private FirebaseAuth mAuth;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = getUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.email_sign_in_button)
    public void signInOrRegister(){
        if (validateForm()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = getUser();
                                updateUI(user);
                            } else {
                               createUser();
                            }
                        }
                    });
        }
   }

    private void createUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = getUser();
                            updateUI(user);
                        } else {
                            Log.d("LoginActivity", "Error with creating user");
                        }
                    }
                });
    }

    public boolean validateForm() {
        boolean isValid = true;

        email = mEmailView.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            mEmailInputLayout.setError("This field is required");
            isValid = false;
        } else {
            mEmailInputLayout.setError(null);
        }

        password = mPasswordView.getText().toString().trim();
        if (TextUtils.isEmpty(password)){
            mPasswordInputLayout.setError("This field is required");
            isValid = false;
        } else {
            mPasswordInputLayout.setError(null);
        }

        return isValid;
    }

    private FirebaseUser getUser(){
        return mAuth.getCurrentUser();
    }
}

