package com.example.studybuddy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("userEmail", null);

        if (savedEmail != null) {
            navigateToNextActivity(savedEmail);
        } else {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }

        private void navigateToNextActivity(String email) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean isSetUp = dbHelper.isSetUp(email);

        Intent intent;
        if (isSetUp) {
            intent = new Intent(WelcomeActivity.this, MatchUserActivity.class);
        } else {
            intent = new Intent(WelcomeActivity.this, UserProfileActivity.class);
        }
        startActivity(intent);
    }
}
