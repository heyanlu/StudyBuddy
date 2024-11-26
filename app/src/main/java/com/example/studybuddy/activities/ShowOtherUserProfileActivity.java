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
    //Need to fix otherUserID.
    //otherUserID should be the ID retrieved from MatchUserActivity
    private String otherUserID;
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
                sendConnectionRequest(otherUserID);
            }
        });

        Button returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowOtherUserProfileActivity.this, MatchUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void sendConnectionRequest(String receiverID) {
        // Retrieve userID from Login Activity
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        String myUserID = sharedPreferences.getString("userID", null);
        if (myUserID != null) {
            Log.d("ShowOtherProfileActivity", "userID: " + myUserID);
        } else {
            Toast.makeText(this, "UserID not found!", Toast.LENGTH_SHORT).show();
        }
        boolean success = connectionsDB.insertConnectionRequest(myUserID, receiverID);
        if (success) {
            Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Send Request", Toast.LENGTH_SHORT).show();
        }
    }

}