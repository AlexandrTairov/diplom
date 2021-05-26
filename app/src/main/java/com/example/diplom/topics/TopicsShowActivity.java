package com.example.diplom.topics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.diplom.R;
import com.example.diplom.database.DBHelper;
import com.example.diplom.settings.GeneralSettings;

public class TopicsShowActivity extends Activity {

    ListView topicList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_topics);
        topicList = findViewById(R.id.list);

        databaseHelper = new DBHelper(getApplicationContext());

        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from MQTT ", null);
        String[] headers = new String[] {"name", "value"};
        userAdapter = new SimpleCursorAdapter(this, R.layout.custom_two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        topicList.setAdapter(userAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }

    public void back(View view) {
        Intent intent = new Intent(this, GeneralSettings.class);
        startActivity(intent);
    }

    public void openTopic(View view) {
        Intent intent = new Intent(this, TopicHelper.class);
        TextView Text = findViewById(android.R.id.text1);
        String title = Text.getText().toString();
        intent.putExtra("custom_title", title);
        startActivity(intent);
    }
}
