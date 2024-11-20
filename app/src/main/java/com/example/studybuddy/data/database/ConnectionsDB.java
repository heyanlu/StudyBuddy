package com.example.studybuddy.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionsDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Connections.db";
    public static final String TABLE_NAME = "Connections";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SENDER";
    public static final String COL_3 = "RECEIVER";
    public static final String COL_4 = "STATUS";

    public ConnectionsDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SENDER TEXT, RECEIVER TEXT, STATUS TEXT)");
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

}




