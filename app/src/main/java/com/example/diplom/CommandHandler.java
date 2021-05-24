package com.example.diplom;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Objects;

class CommandHandler {
    private Cursor mCursor;
    private final MQTTClient mMQTTClient;
    private final Database mSQLiteDatabase;

    CommandHandler(MQTTClient MQTTClient, Database mSQLiteDatabase) {
        this.mSQLiteDatabase = mSQLiteDatabase;
        ContentValues mContentValues = new ContentValues();
        mMQTTClient = MQTTClient;
    }

    void Handler(String result) {
//        mCursor = mSQLiteDatabase.query(true, Database.TABLE_NAME_KEYPHRASE,
//                new String[]{Database.id, Database.phrase_1, Database.phrase_2,
//                        Database.phrase_3, Database.phrase_4, Database.TTSPhrase_1,
//                        Database.TTSPhrase_2},
//                        "UPPER(" + Database.phrase_1 + ") LIKE '%" + result + "%' OR " +
//                        "UPPER(" + Database.phrase_2 + ") LIKE '%" + result + "%' OR " +
//                        "UPPER(" + Database.phrase_3 + ") LIKE '%" + result + "%' OR " +
//                        "UPPER(" + Database.phrase_4 + ") LIKE '%" + result + "%' OR " +
//                        "UPPER(" + Database.TTSPhrase_1 + ") LIKE '%" + result + "%' OR " +
//                                "UPPER(" + Database.TTSPhrase_2 + ") LIKE '%" + result + "%'", null, null, null, null, null);
        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            if (Objects.equals(result,
                    mCursor.getString(mCursor.getColumnIndexOrThrow(Database.phrase_1)))) {
                Command(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.id)), Database.phrase_1);
            } else if (Objects.equals(result,
                    mCursor.getString(mCursor.getColumnIndexOrThrow(Database.phrase_2)))) {
                Command(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.id)), Database.phrase_2);
            } else if (Objects.equals(result,
                    mCursor.getString(mCursor.getColumnIndexOrThrow(Database.phrase_3)))) {
                Command(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.id)), Database.phrase_3);
            } else if (Objects.equals(result,
                    mCursor.getString(mCursor.getColumnIndexOrThrow(Database.phrase_4)))) {
                Command(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.id)), Database.phrase_4);
            } else if (Objects.equals(result,
                    mCursor.getString(mCursor.getColumnIndexOrThrow(Database.TTSPhrase_1)))) {
                Command(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.id)), Database.TTSPhrase_1);
            } else if (Objects.equals(result,
                    mCursor.getString(mCursor.getColumnIndexOrThrow(Database.TTSPhrase_2)))) {
                Command(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.id)), Database.TTSPhrase_2);
            }
        }
    }
    private void Command(String id, String column) {
        mCursor.close();
//        mCursor = mSQLiteDatabase.query(true, Database.TABLE_NAME_MQTT,
//                new String[]{Database.id, Database.command, Database.value, Database.topic},
//                Database.id + "=?", new String[]{id}, null, null, null, null);
        mCursor.moveToFirst();
        String value = mCursor.getString(mCursor.getColumnIndexOrThrow(Database.value));
        final String topic = mCursor.getString(mCursor.getColumnIndexOrThrow(Database.topic));
        if(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.command))!=null) {
            switch
            (mCursor.getString(mCursor.getColumnIndexOrThrow(Database.command))) {
                case "activate_deactivate":
                    mCursor.close();
//                    mCursor = mSQLiteDatabase.query(true,
//                            Database.TABLE_NAME_PARAMETERS,
//                            new String[]{Database.id, Database.parameters_1,
//                                    Database.parameters_2},
//                            Database.id + "=?", new String[]{id}, null, null,
//                            null, null);
                    if (Objects.equals(column, Database.phrase_1)) {
                        value = mCursor.getString(mCursor.getColumnIndexOrThrow(Database.parameters_1));
                    } else if (Objects.equals(column, Database.phrase_2)) {
                        value = mCursor.getString(mCursor.getColumnIndexOrThrow(Database.parameters_2));
                    }
                    try {
                        mMQTTClient.publishMessage(topic, value);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            mCursor.close();
//            mCursor = mSQLiteDatabase.query(true, Database.TABLE_NAME_TTS,
//                    new String[]{Database.id, Database.TTS, Database.TTSText},
//                    Database.id + "=?", new String[]{id}, null, null, null, null);
            mCursor.moveToFirst();
            if(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.TTS))!=null) {
                switch(mCursor.getString(mCursor.getColumnIndexOrThrow(Database.TTS))) {
                    case "value_on_command":
                        String text =mCursor.getString(mCursor.getColumnIndexOrThrow(Database.TTSText));
                        if(text!=null){
//                            MyHomeService.mTTSHandler.speak(text + " " + value);
                        } else {
//                            MyHomeService.mTTSHandler.speak(value);
                        }
                        break;
                }
            }
        }
    }
}
