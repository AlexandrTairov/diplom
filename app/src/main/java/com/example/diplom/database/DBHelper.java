package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        super(context, "mqtt", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- on Create database mqtt---");

        db.execSQL("create table MQTT ("
                + "_ID integer primary key autoincrement,"
                + "NAME text,"
                + "VALUE text,"
                + "ACTIVE integer,"
                + "DASHBOARD integer,"
                + "ALTER_NAME text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}