package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MQTTDBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public MQTTDBHelper(Context context) {
        super(context, "mqtt", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table MQTT ("
                + "_id integer primary key autoincrement,"
                + "NAME text,"
                + "VALUE text,"
                + "ACTIVE integer,"
                + "DASHBOARD integer,"
                + "ALTER_NAME text,"
                + "SUBSCRIBE integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MQTT");
        onCreate(db);
    }
}