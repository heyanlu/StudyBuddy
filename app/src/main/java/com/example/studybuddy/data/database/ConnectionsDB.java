package com.example.studybuddy.data.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.studybuddy.data.model.Connections;
import com.example.studybuddy.data.model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectionsDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Connections.db";
    public static final String TABLE_NAME = "Connections";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SENDER_ID";
    public static final String COL_3 = "RECEIVER_ID";
    public static final String COL_4 = "STATUS";

    public ConnectionsDB(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SENDER_ID TEXT, RECEIVER_ID TEXT, STATUS TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertConnectionRequest(String senderID, String receiverID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, senderID);
        contentValues.put(COL_3, receiverID);
        contentValues.put(COL_4, "Pending");
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }



  //This method is used to show all the "likes" when clicking "Like"
    public ArrayList<Connections> getConnectionRequests(String receiverID) {
        if (receiverID == null) {
            Log.e("ConnectionsDB", "receiverID is null. Returning empty list.");
            return new ArrayList<>();
        }
        ArrayList<Connections> connections = new ArrayList<>();
        SQLiteDatabase connectDB = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + " = ?";
        Cursor cursor = connectDB.rawQuery(query, new String[]{receiverID});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String connectionID = cursor.getString(cursor.getColumnIndex(COL_1));
                @SuppressLint("Range") String senderID = cursor.getString(cursor.getColumnIndex(COL_2));
                @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex(COL_4));
                Connections c = new Connections(connectionID, senderID, receiverID, status);
                connections.add(c);
                cursor.close();
            } while (cursor.moveToNext());
            cursor.close();
        }
        connectDB.close();
        return connections;
    }

}




