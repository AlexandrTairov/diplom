package com.example.diplom;

import android.content.ContentResolver;
import android.content.Context;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String TABLE_NAME_KEYPHRASE = "KEYPHRASE";
    public static final String TABLE_NAME_MQTT = "MQTT";
    public static final String TABLE_NAME_PARAMETERS = "PARAMETERS";
    public static final String TABLE_NAME_TTS = "TTS";
    public static final String TTS = "tt1";

    public static String id;

    public static String value;

    public static String phrase_1;

    public static String phrase_2;

    public static String phrase_3;

    public static String phrase_4;

    public static String TTSPhrase_1;

    public static String TTSPhrase_2;
    public static String command;
    public static String topic;
    public static String parameters_1;
    public static String parameters_2;
    public static String TTSText;

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_MQTT +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TOPIC_NAME TEXT," +
                "VALUE TEXT," +
                "CHANGE_DATE TEXT," +
                "TOPIC_ACTION TEXT," +
                "ADDITIONAL_PARAMETERS TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_KEYPHRASE +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERS_COMMAND TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_PARAMETERS +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEXT_PARAMETER TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_TTS +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void clearDatabase() {
        System.out.println("Hello, you drop database");
        SQLiteDatabase db = null;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MQTT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_KEYPHRASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PARAMETERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TTS);
    }

}
