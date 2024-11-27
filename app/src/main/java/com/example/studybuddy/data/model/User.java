package com.example.studybuddy.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Define the structure of user data based on the APIs doc
 */
public class User {
    private String userID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String occupation;
    private ArrayList<String> preferredStudyTime;
    private ArrayList<String> topicInterested;
    private String studyDifficultyLevel;

    private List<String> connections;



    public User(String email, String password, String firstName, String lastName, int age,
                String gender, ArrayList<String> preferredStudyTime, ArrayList<String> topicInterested,
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
        this.connections = new ArrayList<>();
    }
    public User(String email, String password, String firstName, String lastName, int age,
                String gender, ArrayList<String> preferredStudyTime, ArrayList<String> topicInterested,
                String studyDifficultyLevel, String occupation) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.age = age;
        this.gender = gender;
        this.preferredStudyTime = preferredStudyTime;
        this.topicInterested = topicInterested;
        this.studyDifficultyLevel = studyDifficultyLevel;
        this.connections = new ArrayList<>();
    }


    public User(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFormattedStudyTime() {
        return String.join(", ", preferredStudyTime);
    }

    public String getStudyDifficultyLevel() {
        return studyDifficultyLevel;
    }

    public ArrayList<String> getPreferredStudyTime() {
        return preferredStudyTime;
    }

    public ArrayList<String> getTopicInterested() {
        return topicInterested;
    }

    public void addConnection(String email) {
        if (!connections.contains(email)) {
            connections.add(email);
        }
    }

    public void setOccupation(String occupation){
        this.occupation = occupation;
    }

    public String getOccupation(){
        return this.occupation;
    }

    public String getUserID(){
        return this.userID;
    }


    public void removeConnection(String email) {
        connections.remove(email);
    }

    public List<String> getConnections() {
        return connections;
    }


}
