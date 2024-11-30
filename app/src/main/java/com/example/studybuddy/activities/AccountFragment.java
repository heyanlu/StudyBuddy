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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studybuddy.R;
import com.example.studybuddy.data.database.DatabaseHelper;
import com.example.studybuddy.data.model.User;

public class AccountFragment extends Fragment {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextAge, editTextGender, editTextOccupation;
    private Button saveButton, logoutButton;
    private ImageButton editButton;
    private boolean isEditable = false;

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
        String firstName = sharedPreferences.getString("userFirstName", "N/A");
        String lastName = sharedPreferences.getString("userLastName", "N/A");
        String email = sharedPreferences.getString("userEmail", "N/A");
        int age = sharedPreferences.getInt("userAge", 0);
        String gender = sharedPreferences.getString("userGender", "N/A");
        String occupation = sharedPreferences.getString("userOccupation", "N/A");

        // Set the user information in the TextViews
        editTextFirstName.setText("First Name: " + firstName);
        editTextLastName.setText("Last Name: " + lastName);
        editTextEmail.setText("Email: " + email);
        editTextAge.setText("Age: " + age);
        editTextGender.setText("Gender: " + gender);
        editTextOccupation.setText("Occupation: " + occupation);


        editButton.setOnClickListener(v -> toggleEditMode(!isEditable));

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
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        int age;
        try {
            age = editTextAge.getText().toString().isEmpty() ? 0 : Integer.parseInt(editTextAge.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Invalid age input", Toast.LENGTH_SHORT).show();
            return;
        }
        String gender = editTextGender.getText().toString();
        String occupation = editTextOccupation.getText().toString();

        firstName = firstName.isEmpty() ? "N/A" : firstName;
        lastName = lastName.isEmpty() ? "N/A" : lastName;
        email = email.isEmpty() ? "N/A" : email;
        gender = gender.isEmpty() ? "N/A" : gender;
        occupation = occupation.isEmpty() ? "N/A" : occupation;

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        boolean isUpdated = dbHelper.updateUserProfile(email, firstName, lastName, age, gender, occupation);

        if (isUpdated) {
            toggleEditMode(false); // Disable edit mode after saving
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Error updating profile", Toast.LENGTH_SHORT).show();
        }

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
