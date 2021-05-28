package com.example.diplom.topics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.diplom.R;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.settings.GeneralSettings;

public class TopicsShowActivity extends Activity {

    ListView topicList;
    MQTTDBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String[] headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_topics);
        topicList = findViewById(R.id.list);

        databaseHelper = new MQTTDBHelper(getApplicationContext());

        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from MQTT ", null);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();

    }

    public void back(View view) {
        Intent intent = new Intent(this, GeneralSettings.class);
        startActivity(intent);
    }

}
