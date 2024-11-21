package com.example.studybuddy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPassword;
    private DatabaseHelper dbHelper;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonSignup = findViewById(R.id.buttonSignup);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);

//        if (userEmail != null) {
//            navigateToNextActivity(userEmail);
//            finish();
//        }

        // Testing
        if (userEmail != null) {
            Log.d("LoginActivity", "User is logged in with email: " + userEmail);
            navigateToNextActivity(userEmail);
        } else {
            Log.d("LoginActivity", "No logged-in user found.");
        }

        buttonLogin.setOnClickListener(view -> {
            email = editTextLoginEmail.getText().toString();
            String password = editTextLoginPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = dbHelper.validateUser(email, password);
            if (isValid) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userEmail", email);
                editor.apply();
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                //store userID and pass it to ShowUserProfile for connecting purpose
                String userID = dbHelper.getUserIDByEmail(email);
                editor.putString("userID", userID);
                editor.apply();

                boolean isSetUp = dbHelper.isSetUp(email);
                Intent intent;
                if (!isSetUp) {
                    intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, MatchUserActivity.class);
                }
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void navigateToNextActivity(String email) {
        boolean isSetUp = dbHelper.isSetUp(email);
        Intent intent;
        if (!isSetUp) {
            intent = new Intent(LoginActivity.this, UserProfileActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, MatchUserActivity.class);
        }
        startActivity(intent);
    }
}
