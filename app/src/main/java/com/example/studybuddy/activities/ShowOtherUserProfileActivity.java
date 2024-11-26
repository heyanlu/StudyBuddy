package com.example.studybuddy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.ConnectionsDB;

public class ShowOtherUserProfileActivity extends AppCompatActivity {
    //Notes for Aarzoo:
    //currentUserEmail should be retrieved from MatchUserActivity
    private String currentUserEmail;
    ConnectionsDB connectionsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);
        connectionsDB = new ConnectionsDB(this);
        Button connectBtn = findViewById(R.id.connect);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConnectionRequest(currentUserEmail);
            }
        });
    }

    private void sendConnectionRequest(String receiverEmail) {
        // Retrieve userEmail from Login Activity
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String myUserEmail = sharedPreferences.getString("userEmail", null);
        if (myUserEmail != null) {
            Log.d("ShowOtherProfileActivity", "userEmail: " + myUserEmail);
        } else {
            Toast.makeText(this, "Your Email not found!", Toast.LENGTH_SHORT).show();
        }
        boolean success = connectionsDB.insertConnectionRequest(myUserEmail, receiverEmail);
        if (success) {
            Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Send Request", Toast.LENGTH_SHORT).show();
        }
    }

}