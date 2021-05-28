package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class ActionDBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public ActionDBHelper(Context context) {
        super(context, "actions", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- on Create database actions---");

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
