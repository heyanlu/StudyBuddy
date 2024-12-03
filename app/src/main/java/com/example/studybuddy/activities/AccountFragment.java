package com.example.studybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    private TextView userEmailTextView, editTopic, myTopics, myTime, myDifficultyLevel, myFirstName, myLastName, myAge, myGender, myOccupation, myEmail;
    private Button logoutButton;
    private ImageButton editButton, editPersonalInfo, editTimePreference, editDifficultyPreference, editLoginInfo;
    private boolean isEditable = false;
    private DatabaseHelper dbHelper;
    StringBuilder updatedSelectedTopics = new StringBuilder();
    String updatedDifficulty;
    StringBuilder timePreferences = new StringBuilder();
    ImageButton editMyTopics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_account, container, false);

       // editTextFirstName = view.findViewById(R.id.editTextFirstName);
       // editTextLastName = view.findViewById(R.id.editTextLastName);
      //  editTextEmail = view.findViewById(R.id.editTextEmail);
        //editTextAge = view.findViewById(R.id.editTextAge);
        //editTextGender = view.findViewById(R.id.editTextGender);
       // editTextOccupation = view.findViewById(R.id.editTextOccupation);

        logoutButton = view.findViewById(R.id.logoutButton);
        //editButton = view.findViewById(R.id.editButton);
      // EditText passwordTextView = view.findViewById(R.id.passwordEditText);





        myTopics = view.findViewById(R.id.my_topics);
        myTime = view.findViewById(R.id.my_time);
        myDifficultyLevel = view.findViewById(R.id.my_difficulty_level);
        myFirstName = view.findViewById(R.id.first_name);
        myLastName = view.findViewById(R.id.last_name);
        myAge = view.findViewById(R.id.my_age);
        myOccupation = view.findViewById(R.id.my_occupation);
        myGender = view.findViewById(R.id.my_gender);
        myEmail = view.findViewById(R.id.my_email);

        editMyTopics = view.findViewById(R.id.editTopicPreference);
        editPersonalInfo = view.findViewById(R.id.editPersonalInfo);
        editTimePreference = view.findViewById(R.id.editTimePreference);
        editDifficultyPreference = view.findViewById(R.id.editDifficultyPreference);
        userEmailTextView = view.findViewById(R.id.user_email);
        editLoginInfo = view.findViewById(R.id.editLoginInfo);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", null);

        EditText passwordEditText = view.findViewById(R.id.passwordEditText);

        dbHelper = new DatabaseHelper(requireContext());

        userEmailTextView.setText(userEmail);
       // DatabaseHelper db = new DatabaseHelper(view.getContext());
        passwordEditText.setText(dbHelper.getPassword(userEmail));
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Add a checkbox or toggle to show/hide password
        CheckBox showPasswordCheckBox = view.findViewById(R.id.showPasswordCheckBox);
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show password
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Hide password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Move cursor to the end of the text
            passwordEditText.setSelection(passwordEditText.length());
        });




        User currentUser = dbHelper.getUserInfoByEmail(userEmail);

        //Calling the Edit Activity when the person clicks on the edit
        editMyTopics.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), EditMyTopicPreferences.class);
            intent.putExtra("userEmail", userEmail);
            intent.putExtra("topics", currentUser.getTopicInterested());
            startActivity(intent);
        });

        editDifficultyPreference.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), EditDifficultyLevel.class);
            intent.putExtra("userEmail", userEmail);
            intent.putExtra("userDifficultyLevel", currentUser.getStudyDifficultyLevel());
            startActivity(intent);
        });

        editTimePreference.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), EditPreferredTime.class);
            intent.putExtra("userEmail", userEmail);
            startActivity(intent);
        });

        editPersonalInfo.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), EditPersonalInformation.class);
            intent.putExtra("userEmail", userEmail);
            intent.putExtra("userFirstName", currentUser.getFirstName());
            intent.putExtra("userLastName", currentUser.getLastName());
            intent.putExtra("userAge", String.valueOf(currentUser.getAge()));
            intent.putExtra("userOccupation", String.valueOf(currentUser.getOccupation()));
            intent.putExtra("userGender", currentUser.getGender());
            startActivity(intent);
        });

        editLoginInfo.setOnClickListener( v -> {
            Intent intent = new Intent(view.getContext(), ResetPassword.class);
            intent.putExtra("userEmail", userEmail);
            startActivity(intent);

        });

        myTopics.setText(dbHelper.getUserTopicString(userEmail));
        myTime.setText(currentUser.getFormattedStudyTime());
        myDifficultyLevel.setText(currentUser.getStudyDifficultyLevel());
        myAge.setText("Age: "+currentUser.getAge());
        myEmail.setText("Email: "+userEmail);
        myGender.setText("Gender: " + currentUser.getGender());
        myFirstName.setText("First Name: "+currentUser.getFirstName());
        myLastName.setText("Last Name: "+ currentUser.getLastName());
        myOccupation.setText("Occupation: "+currentUser.getOccupation());


//        editTextFirstName.setText("First Name: " + (currentUser.getFirstName() != null ? currentUser.getFirstName() : "N/A"));
//        editTextLastName.setText("Last Name: " + (currentUser.getLastName() != null ? currentUser.getLastName() : "N/A"));
//        editTextEmail.setText("Email: " + (currentUser.getEmail() != null ? currentUser.getEmail() : "N/A"));
//        editTextAge.setText("Age: " + (currentUser.getAge() > 0 ? currentUser.getAge() : 0));
//        editTextGender.setText("Gender: " + (currentUser.getGender() != null ? currentUser.getGender() : "N/A"));
//        editTextOccupation.setText("Occupation: " + (currentUser.getOccupation() != null ? currentUser.getOccupation() : "N/A"));

        //开始加入
        //Update Topic
//        checkComputerScience.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) updatedSelectedTopics.append("Computer Science ");
//            else removeTopic(updatedSelectedTopics, "Computer Science ");
//        });
//        checkBiology.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) updatedSelectedTopics.append("Biology ");
//            else removeTopic(updatedSelectedTopics, "Biology ");
//        });
//        checkChemistry.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) updatedSelectedTopics.append("Chemistry ");
//            else removeTopic(updatedSelectedTopics, "Chemistry ");
//        });
//        checkMathematics.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) updatedSelectedTopics.append("Mathematics ");
//            else removeTopic(updatedSelectedTopics,"Mathematics");
//        });
//
//        checkEngineering.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) updatedSelectedTopics.append("Engineering ");
//            else removeTopic(updatedSelectedTopics,"Engineering");
//        });
//        checkPhysics.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked) updatedSelectedTopics.append("Physics ");
//            else removeTopic(updatedSelectedTopics, "Physics ");
//        });
//       checkEnglish.setOnCheckedChangeListener((buttonView, isChecked) -> {
//           if(isChecked) updatedSelectedTopics.append("English ");
//           else removeTopic(updatedSelectedTopics, "Computer Science ");
//       });
//       checkFrench.setOnCheckedChangeListener((buttonView, isChecked) -> {
//           if(isChecked) updatedSelectedTopics.append("French ");
//           else removeTopic(updatedSelectedTopics, "French ");
//       });
//       checkHistory.setOnCheckedChangeListener((buttonView, isChecked) -> {
//           if(isChecked) updatedSelectedTopics.append("History ");
//           else removeTopic(updatedSelectedTopics, "History ");
//       });
//       checkPhilosophy.setOnCheckedChangeListener((buttonView, isChecked) -> {
//           if(isChecked) updatedSelectedTopics.append("Philosophy ");
//           else removeTopic(updatedSelectedTopics, "Philosophy ");
//       });
//        if (updatedSelectedTopics.length() > 0) {
//            updatedSelectedTopics.setLength(updatedSelectedTopics.length() - 2);
//        }
//        String[] topics = updatedSelectedTopics.toString().split(" ");
//        ArrayList<String> topicList = new ArrayList<>(Arrays.asList(topics));
//        currentUser.setTopicInterested(topicList);
//        dbHelper.updateUserTopic(userEmail, updatedSelectedTopics.toString());
//
//        //Update Difficulty Level
//        radioGroupDifficulty.setOnCheckedChangeListener((group, checkedId) -> {
//            RadioButton selectedRadioButton = group.findViewById(checkedId);
//            updatedDifficulty = selectedRadioButton.getText().toString();
//            currentUser.setStudyDifficultyLevel(updatedDifficulty);
//            dbHelper.updateUserStudyDifficultyLevel(userEmail, updatedSelectedTopics.toString());
//        });
//
//
//        //Update Study Time
//
//        checkWeekdayMorning.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked) timePreferences.append("Weekday Morning ");
//            else removeTime(timePreferences, "Weekday Morning ");
//        });
//        checkWeekdayAfternoon.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked) timePreferences.append("Weekday Afternoon ");
//            else removeTime(timePreferences, "Weekday Afternoon ");
//        });
//        checkWeekdayEvening.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked) timePreferences.append("Weekday Evening ");
//            else removeTime(timePreferences, "Weekday Evening ");
//        });
//        checkWeekendMorning.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked) timePreferences.append("Weekend Morning ");
//            else removeTime(timePreferences, "Weekend Morning ");
//        });
//        checkWeekendAfternoon.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked) timePreferences.append("Weekend Afternoon ");
//            else removeTime(timePreferences, "Weekend Afternoon ");
//        });
//        checkWeekendEvening.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) timePreferences.append("Weekend Evening ");
//            else removeTime(timePreferences, "Weekend Evening ");
//        });
//        if (timePreferences.length() > 0) {
//            timePreferences.setLength(timePreferences.length() - 2);
//        }
//        String[] times = timePreferences.toString().split(",");
//        ArrayList<String> timeList = new ArrayList<>(Arrays.asList(times));
//        currentUser.setPreferredStudyTime(timeList);
//        dbHelper.updateUserStudyTime(userEmail, timePreferences.toString());
        //结束加入


//        editButton.setOnClickListener(v -> {
//            if (isEditable) {
//                saveUserData();
//            }
//            toggleEditMode(!isEditable);
//        });

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

        //开始加入
        String topics = updatedSelectedTopics.toString();
        String time = timePreferences.toString();
        String level = updatedDifficulty;
        //结束加入

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


        boolean isUpdated = dbHelper.updateUserProfile(userEmail, firstName, lastName, age, gender, topics, time, level);

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

    private StringBuilder removeTopic(StringBuilder topics, String topic) {
        int index = topics.indexOf(topic);
        topics.delete(index, index + topic.length());
        return topics;
    }

    private StringBuilder removeTime(StringBuilder times, String time) {
        int index = times.indexOf(time);
        times.delete(index, index + time.length());
        return times;
    }

}
