package com.example.studybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.ConnectionsDB;

public class ShowUserProfileActivity extends AppCompatActivity {
    private String currentUserID;
    ConnectionsDB connections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);
        connections = new ConnectionsDB(this);
        Button connectBtn = findViewById(R.id.connect);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConnectionRequest(currentUserID);
            }
        });

        Button returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUserProfileActivity.this, MatchUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void sendConnectionRequest(String senderID) {
        Intent intent = getIntent();
        String receiverID = intent.getStringExtra("userID");
        boolean success = connections.insertConnectionRequest(receiverID, senderID);
        if (success) {
            Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to Send Request", Toast.LENGTH_SHORT).show();
        }
    }
}