package com.example.studybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;

public class AccountFragment extends Fragment {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextAge, editTextGender, editTextOccupation;
    private Button logoutButton;
    private ImageButton editButton;
    private boolean isEditable = false;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_account, container, false);

        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextGender = view.findViewById(R.id.editTextGender);
        editTextOccupation = view.findViewById(R.id.editTextOccupation);
        logoutButton = view.findViewById(R.id.logoutButton);
        editButton = view.findViewById(R.id.editButton);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);
        dbHelper = new DatabaseHelper(requireContext());

        User currentUser = dbHelper.getUserInfoByEmail(userEmail);

        editTextFirstName.setText("First Name: " + (currentUser.getFirstName() != null ? currentUser.getFirstName() : "N/A"));
        editTextLastName.setText("Last Name: " + (currentUser.getLastName() != null ? currentUser.getLastName() : "N/A"));
        editTextEmail.setText("Email: " + (currentUser.getEmail() != null ? currentUser.getEmail() : "N/A"));
        editTextAge.setText("Age: " + (currentUser.getAge() > 0 ? currentUser.getAge() : 0));
        editTextGender.setText("Gender: " + (currentUser.getGender() != null ? currentUser.getGender() : "N/A"));
        editTextOccupation.setText("Occupation: " + (currentUser.getOccupation() != null ? currentUser.getOccupation() : "N/A"));


        editButton.setOnClickListener(v -> {
            if (isEditable) {
                saveUserData();
            }
            toggleEditMode(!isEditable);
        });

        logoutButton.setOnClickListener(v -> logout());

        return view;
    }

    private void toggleEditMode(boolean enable) {
        isEditable = enable;

        editTextFirstName.setEnabled(enable);
        editTextLastName.setEnabled(enable);
        editTextEmail.setEnabled(enable);
        editTextAge.setEnabled(enable);
        editTextGender.setEnabled(enable);
        editTextOccupation.setEnabled(enable);

        editButton.setImageResource(enable ? R.drawable.save : R.drawable.edit);
    }

    private void saveUserData() {
        Log.d("AccountFragment", "saveUserData called");

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);

        if (userEmail == null) {
            Toast.makeText(requireContext(), "User email not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String firstName = getTextWithoutLabel(editTextFirstName, "First Name: ");
        String lastName = getTextWithoutLabel(editTextLastName, "Last Name: ");
        String email = getTextWithoutLabel(editTextEmail, "Email: ");
        String gender = getTextWithoutLabel(editTextGender, "Gender: ");
        String occupation = getTextWithoutLabel(editTextOccupation, "Occupation: ");

        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = 0;
        try {
            age = Integer.parseInt(getTextWithoutLabel(editTextAge, "Age: "));
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Invalid age input", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("AccountFragment", "First Name: " + firstName);
        Log.d("AccountFragment", "Last Name: " + lastName);

        boolean isUpdated = dbHelper.updateUserProfile(userEmail, firstName, lastName, age, gender, occupation);

        if (isUpdated) {
            Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userEmail", email);
            editor.apply();

        } else {
            Toast.makeText(requireContext(), "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getTextWithoutLabel(EditText editText, String label) {
        String text = editText.getText().toString().trim();
        if (text.startsWith(label)) {
            return text.substring(label.length()).trim();
        }
        return text;
    }


    private void logout() {
        Log.d("AccountFragment", "Logging out...");

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userEmail");
        editor.apply();

        Log.d("AccountFragment", "User data cleared. Navigating to LoginActivity.");

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        requireActivity().finish();
    }
}
