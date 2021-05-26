package com.example.diplom.topics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.diplom.R;
import com.example.diplom.database.DBHelper;
import com.example.diplom.settings.GeneralSettings;

public class TopicHelper extends Activity {

    TextView textView;
    String title;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        Bundle arguments = getIntent().getExtras();
        setContentView(R.layout.topic);
        if (arguments != null) {
            title = arguments.get("custom_title").toString();
            textView = this.findViewById(R.id.title);
            textView.setTextSize(36);
            textView.setGravity(1);
            textView.setText(title);
        }
//        setContentView(textView);
//        databaseHelper = new DBHelper(getApplicationContext());
//
//        db = databaseHelper.getReadableDatabase();
////        userCursor = db.rawQuery("select * from MQTT where NAME = " + " ", null);
//        String[] headers = new String[] {"name", "value", "active", "dashboard", "alter_name"};
//        userAdapter = new SimpleCursorAdapter(this, R.layout.custom_item,
//                userCursor, headers, new int[]{R.id.text}, 0);
//        title.setAdapter(userAdapter);
    }

    public void back(View view) {
        Intent intent = new Intent(this,TopicsShowActivity.class);
        startActivity(intent);
    }

}
