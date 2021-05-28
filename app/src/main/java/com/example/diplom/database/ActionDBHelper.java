package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ActionDBHelper extends SQLiteOpenHelper {

    public ActionDBHelper(Context context) {
        super(context, "actions", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table ACTIONS ("
                + "_id integer primary key autoincrement,"
                + "ACTION_VALUE text,"
                + "TOPIC_NAME text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ACTIONS");
        onCreate(db);
    }
}
