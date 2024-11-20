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

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_user);

        ImageView dropdownIcon = findViewById(R.id.dropdownIcon);

        dropdownIcon.setOnClickListener(view -> showPopupMenu(view));

//        dropdownIcon.setOnClickListener(view -> {
//            Intent intent = new Intent(MatchUserActivity.this, AccountActivity.class);
//            startActivity(intent);
//        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        List<String> currentUserTopics = new ArrayList<>();
        currentUserTopics.add("Math");
        currentUserTopics.add("English");

        ArrayList<User> users = dbHelper.getUsersWithSameTopics(currentUserTopics); // Correctly declaring ArrayList of User objects

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
                } else if (item.getItemId() == R.id.nav_chat) {
                    openChatPage();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void showPopupMenu(View view) {
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.account) {
                startActivity(new Intent(MatchUserActivity.this, AccountActivity.class));
                return true;
            } else if (menuItem.getItemId() == R.id.setting) {
                startActivity(new Intent(MatchUserActivity.this, SettingActivity.class));
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void openLikesPage() {
        startActivity(new Intent(MatchUserActivity.this, LikesActivity.class));
    }

    private void openGenAIPage() {
        startActivity(new Intent(MatchUserActivity.this, GenAIActivity.class));
    }

    private void openChatPage() {
        startActivity(new Intent(MatchUserActivity.this, chatActivity.class));
    }
}
