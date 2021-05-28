package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MQTTDBHelper extends SQLiteOpenHelper {

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
                + "SUBSCRIBE integer,"
                + "ACTION_VALUE text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MQTT");
        onCreate(db);
    }
}