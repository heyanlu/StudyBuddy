package com.example.studybuddy.activities;
import android.view.MenuItem;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapter.SectionedUserAdapter;
import com.example.studybuddy.adapter.UserAdapter;
import com.example.studybuddy.data.database.ConnectionsDB;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.Connections;
import com.example.studybuddy.data.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Get all the current user's likes from connectionsDB
        ConnectionsDB connectionsDB = new ConnectionsDB(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String myUserEmail = sharedPreferences.getString("userEmail", null);
        if (myUserEmail != null) {
            Log.d("LikeActivity", "userEmail: " + myUserEmail);
        } else {
            Log.e("LikesActivity", "userEmail is null. Cannot retrieve connections.");
            Toast.makeText(this, "userEmail not found!. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Connections> likes = connectionsDB.getConnectionRequests(myUserEmail);

        //Convert "likes" into users
        List<User> users = new ArrayList<>();
        for (Connections like : likes) {
            String senderEmail = like.getSenderEmail();
            DatabaseHelper db = new DatabaseHelper(this);
            // create user by his email
            User user = db.getUserInfoByEmail(senderEmail);
            users.add(user);
        }
        //Create Adapter
        UserAdapter adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);

    }
}


