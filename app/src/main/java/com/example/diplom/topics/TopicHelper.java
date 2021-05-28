package com.example.diplom.topics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.diplom.R;
import com.example.diplom.database.ActionDBHelper;
import com.example.diplom.database.MQTTDBHelper;

public class TopicHelper extends Activity {

    TextView textView;
    String title;
    MQTTDBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    int isActive, isDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        databaseHelper = new MQTTDBHelper(this);
        db = databaseHelper.getWritableDatabase();
        Bundle arguments = getIntent().getExtras();
        setContentView(R.layout.topic);
        if (arguments != null) {
            title = arguments.get("custom_title").toString();
            textView = this.findViewById(R.id.title);
            textView.setTextSize(36);
            textView.setGravity(1);
            textView.setText(title);
        }
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select ACTIVE from MQTT WHERE NAME = '" + title +"'", null);
        userCursor.moveToFirst();
        isActive = userCursor.getInt(userCursor.getColumnIndex("ACTIVE"));
        Switch switchActive = findViewById(R.id.switch1);
        if (isActive == 0) {
            switchActive.setChecked(false);
        } else {
            switchActive.setChecked(true);
        }
        userCursor = db.rawQuery("select DASHBOARD from MQTT WHERE NAME = '" + title + "'", null);
        userCursor.moveToFirst();
        isDashboard = userCursor.getInt(userCursor.getColumnIndex("DASHBOARD"));
        Switch switchDashboard = findViewById(R.id.switch2);
        if (isDashboard == 0) {
            switchDashboard.setChecked(false);
        } else {
            switchDashboard.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userCursor.close();
        databaseHelper.close();
        db.close();
    }

    public void back(View view) {
        Intent intent = new Intent(this, TopicsShowActivity.class);
        startActivity(intent);
    }

    public void deleteTopic(View view) {

        databaseHelper = new MQTTDBHelper(this);
        db = databaseHelper.getWritableDatabase();
        TextView Text = findViewById(R.id.title);
        String name = Text.getText().toString();
        int count = db.delete("MQTT", "NAME = " + "'" + name + "'", null);


        ActionDBHelper dbHelper = new ActionDBHelper(this);
        final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("ACTIONS", "TOPIC_NAME = " + "'" + name + "'", null);


        Log.d("myLog ", String.valueOf(count));
        if (count > 0) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout));

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("Successfully deleted");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

            back(view);
        }
    }

    public void openTopicNameChange(final View view) {

        TextView textView = findViewById(R.id.title);
        final String title = textView.getText().toString();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter alter topic name");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("ALTER_NAME", value);
                db.update("MQTT", cv, "NAME = ?", new String[]{title});

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(view.getContext(), TopicsShowActivity.class));
            }
        });

        alert.show();
    }

    public void selectActions(View view) {

        TextView textView = findViewById(R.id.title);
        final String title = textView.getText().toString();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose action");
        String[] items = {"Turn on", "Turn off", "Open", "Close"};
        ActionDBHelper dbHelper = new ActionDBHelper(this);
        final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        final ContentValues cv = new ContentValues();
        final long[] rowID = {0};
        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        cv.put("TOPIC_NAME", title);
                        cv.put("ACTION_VALUE", "Turn on");
                        rowID[0] = sqLiteDatabase.insert("ACTIONS", null, cv);
                        Log.d("myLogs", "row inserted, ID = " + rowID[0]);
                        break;
                    case 1:
                        cv.put("TOPIC_NAME", title);
                        cv.put("ACTION_VALUE", "Turn off");
                        rowID[0] = sqLiteDatabase.insert("ACTIONS", null, cv);
                        Log.d("myLogs", "row inserted, ID = " + rowID[0]);
                        break;
                    case 2:
                        cv.put("TOPIC_NAME", title);
                        cv.put("ACTION_VALUE", "Open");
                        rowID[0] = sqLiteDatabase.insert("ACTIONS", null, cv);
                        Log.d("myLogs", "row inserted, ID = " + rowID[0]);
                        break;
                    case 3:
                        cv.put("TOPIC_NAME", title);
                        cv.put("ACTION_VALUE", "Close");
                        rowID[0] = sqLiteDatabase.insert("ACTIONS", null, cv);
                        Log.d("myLogs", "row inserted, ID = " + rowID[0]);
                        break;
                }
            }
        });


        AlertDialog al = alert.create();
        al.setCanceledOnTouchOutside(false);
        al.show();

    }

    public void changeActive(View view) {

        db = databaseHelper.getReadableDatabase();
        TextView textView = findViewById(R.id.title);
        final String title = textView.getText().toString();
        ContentValues cv = new ContentValues();
        if (isActive == 0) {
            cv.put("ACTIVE", 1);
        } else {
            cv.put("ACTIVE", 0);
        }
        db.update("MQTT", cv, "NAME = ?", new String[]{title});

    }

    public void changeDashboard(View view) {

        db = databaseHelper.getReadableDatabase();
        TextView textView = findViewById(R.id.title);
        final String title = textView.getText().toString();
        ContentValues cv = new ContentValues();
        if (isActive == 0) {
            cv.put("DASHBOARD", 1);
        } else {
            cv.put("DASHBOARD", 0);
        }
        db.update("MQTT", cv, "NAME = ?", new String[]{title});

    }
}
