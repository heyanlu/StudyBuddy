package com.example.studybuddy.data.model;

import java.util.ArrayList;

/**
 * Define the structure of user data based on the APIs doc
 */
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String preferredStudyTime;
    private ArrayList<String> topicInterested;
    private String studyDifficultyLevel;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, int age,
                String gender, String preferredStudyTime, ArrayList<String> topicInterested,
                String studyDifficultyLevel) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.preferredStudyTime = preferredStudyTime;
        this.topicInterested = topicInterested;
        this.studyDifficultyLevel = studyDifficultyLevel;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPreferredStudyTime() {
        return preferredStudyTime;
    }

    public ArrayList<String> getTopicInterested() {
        return topicInterested;
    }

    public String getStudyDifficultyLevel() {
        return studyDifficultyLevel;
    }
}
