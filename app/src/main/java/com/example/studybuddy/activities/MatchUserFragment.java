package com.example.studybuddy.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.adapter.SectionedUserAdapter;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchUserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_match_user, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.usersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

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
        return view;
    }
}
