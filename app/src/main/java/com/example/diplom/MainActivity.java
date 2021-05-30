package com.example.diplom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import com.example.diplom.database.ActionDBHelper;
import com.example.diplom.database.MQTTDBClear;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.database.UserDBHelper;
import com.example.diplom.settings.MQTTSettings;
import com.example.diplom.settings.Settings;
import com.example.diplom.topics.TopicHelper;
import com.example.diplom.topics.TopicsAddActivity;
import com.example.diplom.topics.TopicsShowActivity;


import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, ForegroundActivity.class));

        ListView topicList;
        MQTTDBHelper databaseHelper;
        SQLiteDatabase db;
        Cursor userCursor;
        SimpleCursorAdapter userAdapter;
        String[] headers;

        topicList = findViewById(R.id.list);

        databaseHelper = new MQTTDBHelper(getApplicationContext());

        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from MQTT WHERE DASHBOARD = 1", null);
        headers = new String[] {"NAME", "VALUE"};
        userAdapter = new SimpleCursorAdapter(this, R.layout.custom_two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        topicList.setAdapter(userAdapter);

        topicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(android.R.id.text1);
                String title = textView.getText().toString();
                Intent intent = new Intent(view.getContext(), TopicHelper.class);
                intent.putExtra("custom_title", title);
                startActivity(intent);
            }
        });
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
                    if (text.get(0).equals("connect") || text.get(0).equals("подключиться") || text.get(0).equals("подключись")) {
                        Intent intent = new Intent(this, MQTTSettings.class);
                        startActivity(intent);
                        Button btn = findViewById(R.id.btnConnect);
                        btn.performClick();
                    }
                    if (text.get(0).equals("change user") || text.get(0).equals("поменять пользователя") || text.get(0).equals("пользователь")) {
                        Intent intent = new Intent(this, MQTTSettings.class);
                        startActivity(intent);
                        TextView btn = findViewById(R.id.textView6);
                        btn.performClick();
                    }
                    if (text.get(0).equals("change password") || text.get(0).equals("поменять пароль")) {
                        Intent intent = new Intent(this, MQTTSettings.class);
                        startActivity(intent);
                        TextView btn = findViewById(R.id.textView7);
                        btn.performClick();
                    }
                    if (text.get(0).equals("настроить адрес") || text.get(0).equals("адрес")) {
                        Intent intent = new Intent(this, MQTTSettings.class);
                        startActivity(intent);
                        TextView btn = findViewById(R.id.textView3);
                        btn.performClick();
                    }
                    if (text.get(0).equals("add topic") || text.get(0).equals("добавить топик")) {
                        Intent intent = new Intent(this, TopicsAddActivity.class);
                        startActivity(intent);
                    }
                    if (text.get(0).equals("topic") || text.get(0).equals("топики")) {
                        Intent intent = new Intent(this, TopicsShowActivity.class);
                        startActivity(intent);
                    }
                    if (text.get(0).equals("clear database") || text.get(0).equals("очистить базу данных")) {
                        Intent intent = new Intent(this, MQTTDBClear.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }

}