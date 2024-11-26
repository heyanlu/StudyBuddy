package com.example.studybuddy.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapter.SectionedUserAdapter;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchUserActivity extends AppCompatActivity {

    // TODO: add function to search

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user);


        RecyclerView recyclerView = findViewById(R.id.usersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        List<String> currentUserTopics = new ArrayList<>();
        currentUserTopics.add("Computer Science");
        currentUserTopics.add("Mathematics");
        currentUserTopics.add("Physics");
        currentUserTopics.add("English");
        currentUserTopics.add("French");
        currentUserTopics.add("Engineering");
        currentUserTopics.add("History");
        currentUserTopics.add("Philosophy");
        currentUserTopics.add("Psychology");
        currentUserTopics.add("Music");
        currentUserTopics.add("Art");

        ArrayList<User> users = dbHelper.getUsersWithSameTopics(currentUserTopics);

        Map<String, List<User>> sectionedData = new HashMap<>();
        for (String topic : currentUserTopics) {
            List<User> filteredUsers = new ArrayList<>();
            for (User user : users) {
                if (user.getTopicInterested() != null && user.getTopicInterested().contains(topic)) {
                    filteredUsers.add(user);
                }
            }
            if (!filteredUsers.isEmpty()) {
                sectionedData.put(topic, filteredUsers);
            }
        }

        SectionedUserAdapter adapter = new SectionedUserAdapter(sectionedData);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_matched_buddies) {
                    return true;
                } else if (item.getItemId() == R.id.nav_likes) {
                    openLikesPage();
                    return true;
                } else if (item.getItemId() == R.id.nav_genai) {
                    openGenAIPage();
                    return true;
                } else if (item.getItemId() == R.id.nav_account) {
                    openAccountPage();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void openLikesPage() {
        startActivity(new Intent(MatchUserActivity.this, LikesActivity.class));
    }

    private void openGenAIPage() {
        startActivity(new Intent(MatchUserActivity.this, GenAIActivity.class));
    }

    private void openAccountPage() {
        startActivity(new Intent(MatchUserActivity.this, ShowOtherUserProfileActivity.class));
    }
}