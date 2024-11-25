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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionedUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Map<String, List<User>> sectionedData;
    private List<Object> displayList;
    private Map<String, Boolean> sectionVisibilityMap;

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_USER = 1;

    public SectionedUserAdapter(Map<String, List<User>> sectionedData) {
        this.sectionedData = sectionedData;
        this.displayList = new ArrayList<>();
        this.sectionVisibilityMap = new HashMap<>();
        buildDisplayList();
    }

    private void buildDisplayList() {
        displayList.clear();
        for (Map.Entry<String, List<User>> entry : sectionedData.entrySet()) {
            String section = entry.getKey();
            List<User> users = entry.getValue();

            displayList.add(section);

            if (sectionVisibilityMap.getOrDefault(section, true)) {
                displayList.addAll(users);
            }
        }
        Log.d("SectionedUserAdapter", "Display List: " + displayList.toString());
    }

    public void updateData(Map<String, List<User>> newSectionedData) {
        this.sectionedData = newSectionedData;
        buildDisplayList();
        notifyDataSetChanged();
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
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.sectionTitle.setText(sectionTitle);

            View longUnderline = headerHolder.longUnderline;

            boolean isVisible = sectionVisibilityMap.getOrDefault(sectionTitle, true);
            if (isVisible) {
                headerHolder.sectionTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_down, 0);
                longUnderline.setVisibility(View.GONE);
            } else {
                headerHolder.sectionTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_up, 0);
                longUnderline.setVisibility(View.VISIBLE);
            }

            headerHolder.sectionTitle.setOnClickListener(v -> {
                boolean currentVisibility = sectionVisibilityMap.getOrDefault(sectionTitle, true);
                sectionVisibilityMap.put(sectionTitle, !currentVisibility);

                if (currentVisibility) {
                    headerHolder.sectionTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_up, 0);
                    longUnderline.setVisibility(View.VISIBLE);
                } else {
                    headerHolder.sectionTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_down, 0);
                    longUnderline.setVisibility(View.GONE);
                }

                buildDisplayList();
                notifyDataSetChanged();
            });
        } else {
            User user = (User) displayList.get(position);
            UserViewHolder userHolder = (UserViewHolder) holder;
            userHolder.firstNameTextView.setText(user.getFirstName());
            userHolder.lastNameTextView.setText(user.getLastName());
            userHolder.timeTextView.setText(user.getFormattedStudyTime());
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
        View longUnderline;

        HeaderViewHolder(View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.sectionTitle);
            longUnderline = itemView.findViewById(R.id.longUnderline);
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
