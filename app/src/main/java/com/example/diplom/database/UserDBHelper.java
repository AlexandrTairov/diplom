package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper  extends SQLiteOpenHelper {

    public UserDBHelper(Context context) {
        super(context, "user", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table USER ("
                + "_id integer primary key autoincrement,"
                + "USERNAME text,"
                + "PASSWORD text,"
                + "ADDRESS text,"
                + "CONNECT_ON_LAUNCH integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }
}
