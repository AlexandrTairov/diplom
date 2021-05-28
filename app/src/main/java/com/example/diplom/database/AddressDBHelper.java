package com.example.diplom.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AddressDBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public AddressDBHelper(Context context) {
        super(context, "address", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table ADDRESS ("
                + "_id integer primary key autoincrement,"
                + "ADDRESS text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ADDRESS");
        onCreate(db);
    }
}
