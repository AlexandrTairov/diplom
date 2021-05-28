package com.example.diplom;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import com.example.diplom.database.ActionDBHelper;
import com.example.diplom.database.AddressDBHelper;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.database.UserDBHelper;
import com.example.diplom.settings.Settings;


import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init()
    {

    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void onClickMic(View view)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null)
        {
            switch (requestCode)
            {
                case 10:
                    ArrayList<String> text =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.add("Open settings");
                    if (text.get(0).equals("open settings") || text.get(0).equals("настройки") || text.get(0).equals("открой настройки")) {
                        Intent intent = new Intent(this, Settings.class);
                        startActivity(intent);
                    }
//                    if (text.get(0).equals("drop database") || text.get(0).equals("сброс") || text.get(0).equals("уничтожить бд")) {
//                        View view = null;
//                        dropDatabase(view);
//                        dropDatabaseUser(view);
//                        dropDatabaseAddress(view);
//                        dropDatabaseAction(view);
//                    }
                    break;

            }
        }
    }

    public void dropDatabase(View view) {
        MQTTDBHelper mqttdbHelper = new MQTTDBHelper(this);
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = mqttdbHelper.getWritableDatabase();
        mqttdbHelper.onUpgrade(sqLiteDatabase, 1, 2);
    }

    public void dropDatabaseUser(View view) {
        UserDBHelper userdbHelper = new UserDBHelper(this);
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = userdbHelper.getWritableDatabase();
        userdbHelper.onUpgrade(sqLiteDatabase, 1, 2);
    }

    public void dropDatabaseAddress(View view) {
        AddressDBHelper addressdbHelper = new AddressDBHelper(this);
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = addressdbHelper.getWritableDatabase();
        addressdbHelper.onUpgrade(sqLiteDatabase, 1,2);
    }

    public void dropDatabaseAction(View view) {
        ActionDBHelper actiondbHelper = new ActionDBHelper(this);
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = actiondbHelper.getWritableDatabase();
        actiondbHelper.onUpgrade(sqLiteDatabase, 1, 2);
    }

}