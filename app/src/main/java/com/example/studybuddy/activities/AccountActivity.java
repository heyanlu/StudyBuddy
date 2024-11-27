package com.example.studybuddy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Log.d("AccountActivity", "Logging out...");

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userEmail");
        editor.apply();

        Log.d("AccountActivity", "User data cleared. Navigating to LoginActivity.");

        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });


    }
}

