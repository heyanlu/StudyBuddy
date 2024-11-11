package com.example.studybuddy.data.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studybuddy.data.model.User;

import java.util.ArrayList;

/**
 * Database creation and CRUD operations
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "FIRST_NAME";
    public static final String COL_5 = "LAST_NAME";
    public static final String COL_6 = "AGE";
    public static final String COL_7 = "GENDER";
    public static final String COL_8 = "PREFERRED_STUDY_TIME";
    public static final String COL_9 = "TOPICS_INTERESTED";
    public static final String COL_10 = "STUDY_DIFFICULTY_LEVEL";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, PASSWORD TEXT, FIRST_NAME TEXT, LAST_NAME TEXT, AGE INTEGER, " +
                "GENDER TEXT, PREFERRED_STUDY_TIME TEXT, TOPICS_INTERESTED TEXT, " +
                "STUDY_DIFFICULTY_LEVEL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean updateUserProfile(String email, String firstName, String lastName, int age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, firstName);
        contentValues.put(COL_5, lastName);
        contentValues.put(COL_6, age);
        contentValues.put(COL_7, gender);

        int rowsUpdated = db.update(TABLE_NAME, contentValues, COL_2 + " = ?", new String[]{email});
        db.close();
        return rowsUpdated > 0;
    }

    // Method to update user's topic preference
    public boolean updateUserTopic(String email, String topic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_9, topic);

        int result = db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[]{email});
        db.close();
        return result > 0;
    }

    // Method to update user's study time preference
    public boolean updateUserStudyTime(String email, String studyTimePreference) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_8, studyTimePreference);

        int result = db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[]{email});
        db.close();
        return result > 0;
    }


    public boolean updateUserStudyDifficultyLevel(String email, String difficultyLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_10, difficultyLevel);

        int result = db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[]{email});
        db.close();
        return result > 0;
    }



    //Insert new user when signup
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    //Validate user when login
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    // Update user profile details after login
    public boolean updateUserProfile(int userId, String firstName, String lastName, int age, String gender,
                                     String preferredStudyTime, String topicsInterested, String studyDifficultyLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, firstName);
        contentValues.put(COL_5, lastName);
        contentValues.put(COL_6, age);
        contentValues.put(COL_7, gender);
        contentValues.put(COL_8, preferredStudyTime);
        contentValues.put(COL_9, topicsInterested);
        contentValues.put(COL_10, studyDifficultyLevel);

        int rowsUpdated = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(userId)});
        db.close();
        return rowsUpdated > 0;
    }


    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COL_2));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COL_3));
                @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COL_4));
                @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COL_5));
                @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex(COL_6));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(COL_7));
                @SuppressLint("Range") String studyTime = cursor.getString(cursor.getColumnIndex(COL_8));
                @SuppressLint("Range") String topics = cursor.getString(cursor.getColumnIndex(COL_9));
                @SuppressLint("Range") String difficulty = cursor.getString(cursor.getColumnIndex(COL_10));

                ArrayList<String> studyTimeList = new ArrayList<>();
                if (studyTime != null && !studyTime.isEmpty()) {
                    String[] studyTimeArray = studyTime.split(",");
                    for (String time : studyTimeArray) {
                        studyTimeList.add(time.trim());
                    }
                }

                ArrayList<String> topicsList = new ArrayList<>();
                if (topics != null && !topics.isEmpty()) {
                    String[] topicsArray = topics.split(",");
                    for (String topic : topicsArray) {
                        topicsList.add(topic.trim());
                    }
                }

                User user = new User(email, password, firstName, lastName, age, gender, studyTimeList, topicsList, difficulty);
                users.add(user);

            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return users;
    }






}

