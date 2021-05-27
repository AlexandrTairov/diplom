package com.example.diplom.topics;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
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
    }

    public void back(View view) {
        Intent intent = new Intent(this,TopicsShowActivity.class);
        startActivity(intent);
    }

    public void deleteTopic(View view) {
        databaseHelper = new DBHelper(this);
        db = databaseHelper.getWritableDatabase();
        TextView Text = findViewById(R.id.title);
        String name = Text.getText().toString();
        int count = db.delete("MQTT", "NAME = " + "'" + name + "'", null);
        db.close();
        Log.d("myLog ", String.valueOf(count));
        if (count > 0) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Successfully deleted");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

            back(view);
        }
    }

    public void openTopicNameChange(View view) {
    }

    public void selectActions(View view) {
    }
}
