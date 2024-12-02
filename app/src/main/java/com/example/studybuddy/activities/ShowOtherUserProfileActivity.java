package com.example.studybuddy.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapter.InterestsAdapter;
import com.example.studybuddy.data.database.ConnectionsDB;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;

import java.util.ArrayList;

public class ShowOtherUserProfileActivity extends AppCompatActivity {
    //Notes for Aarzoo:
    //currentUserEmail should be retrieved from MatchUserActivity
    private String currentUserEmail;
    ConnectionsDB connectionsDB;
    DatabaseHelper db ;
    TextView userName, emailTextView, userOccupation;
    private String otherUserEmail;
    User user; //This is the user who we want to send the connection request

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);
        connectionsDB = new ConnectionsDB(this);
        db = new DatabaseHelper(this);
        userName = findViewById(R.id.userName);
        emailTextView = findViewById(R.id.email_textView);
        userOccupation = findViewById(R.id.occupationText);




        Intent intent = getIntent();
        otherUserEmail = intent.getStringExtra("email");

        Button connectBtn = findViewById(R.id.connect);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConnectionRequest(otherUserEmail);
            } //fixed argument
        });

        user = db.getUserInfoByEmail(intent.getStringExtra("email"));
        userName.setText(user.getFirstName() +" "+user.getLastName());
        emailTextView.setText(user.getEmail());
        userOccupation.setText(user.getOccupation());

        //Setting the occupation


        RecyclerView recyclerView = findViewById(R.id.interestsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        InterestsAdapter adapter = new InterestsAdapter(user.getTopicInterested());
        recyclerView.setAdapter(adapter);

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