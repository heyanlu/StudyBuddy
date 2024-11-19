package com.example.studybuddy.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studybuddy.R;
import com.example.studybuddy.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SectionedUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Map<String, List<User>> sectionedData;
    private final List<Object> displayList;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_USER = 1;

    public SectionedUserAdapter(Map<String, List<User>> sectionedData) {
        this.sectionedData = sectionedData;
        this.displayList = new ArrayList<>();

        for (Map.Entry<String, List<User>> entry : sectionedData.entrySet()) {
            displayList.add(entry.getKey());
            displayList.addAll(entry.getValue());
        }
        Log.d("SectionedUserAdapter", "Display List: " + displayList.toString());
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (displayList.get(position) instanceof String) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_USER;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            String sectionTitle = (String) displayList.get(position);
            ((HeaderViewHolder) holder).sectionTitle.setText(sectionTitle);
        } else {
            User user = (User) displayList.get(position);
            ((UserViewHolder) holder).firstNameTextView.setText(user.getFirstName());
            ((UserViewHolder) holder).lastNameTextView.setText(user.getLastName());
            ((UserViewHolder) holder).timeTextView.setText(user.getFormattedStudyTime());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle;

        HeaderViewHolder(View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.sectionTitle);
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView, lastNameTextView, timeTextView;

        UserViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.firstNameTextView);
            lastNameTextView = itemView.findViewById(R.id.lastNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
