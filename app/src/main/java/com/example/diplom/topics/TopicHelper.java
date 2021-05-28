package com.example.diplom.topics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.example.diplom.R;
import com.example.diplom.database.ActionDBHelper;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.database.UserDBHelper;

public class TopicHelper extends Activity {

    TextView textView;
    String title;
    MQTTDBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int isActive, isDashboard, isSubscribe;

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
        userCursor = db.rawQuery("select ACTIVE from MQTT WHERE NAME = '" + title + "'", null);
        userCursor.moveToFirst();
        try {
            isActive = userCursor.getInt(userCursor.getColumnIndex("ACTIVE"));
        } catch (Exception e) {
            isActive = 0;
        }
        Switch switchActive = findViewById(R.id.switch1);
        if (isActive == 0) {
            switchActive.setChecked(false);
        } else {
            switchActive.setChecked(true);
        }
        userCursor = db.rawQuery("select DASHBOARD from MQTT WHERE NAME = '" + title + "'", null);
        userCursor.moveToFirst();
        try {
            isDashboard = userCursor.getInt(userCursor.getColumnIndex("DASHBOARD"));
        } catch (Exception e) {
            isDashboard = 0;
        }
        Switch switchDashboard = findViewById(R.id.switch2);
        if (isDashboard == 0) {
            switchDashboard.setChecked(false);
        } else {
            switchDashboard.setChecked(true);
        }
        userCursor = db.rawQuery("select SUBSCRIBE from MQTT WHERE NAME = '" + title + "'", null);
        userCursor.moveToFirst();
        try {
            isSubscribe = userCursor.getInt(userCursor.getColumnIndex("SUBSCRIBE"));
        } catch (Exception e) {
            isSubscribe = 0;
        }
        Switch switchSubscribe = findViewById(R.id.switch0);
        if (isSubscribe == 0) {
            switchSubscribe.setChecked(false);
        } else {
            switchSubscribe.setChecked(true);
        }

        userCursor = db.rawQuery("select ACTION_VALUE from MQTT WHERE NAME = '" + title + "'", null);
        userCursor.moveToFirst();
        String action = "";
        try {
            action = userCursor.getString(userCursor.getColumnIndex("ACTION_VALUE"));
        } catch (Exception e) {
            action = "";
        }

        TextView textView = findViewById(R.id.textAction);
        textView.setText(action);

        userCursor = db.rawQuery("select ALTER_NAME from MQTT WHERE NAME = '" + title + "'", null);
        userCursor.moveToFirst();
        String alter = "";
        try {
            alter = userCursor.getString(userCursor.getColumnIndex("ALTER_NAME"));
        } catch (Exception e) {
            alter = "Введите альтернативное имя топика";
        }

        textView = findViewById(R.id.textView5);
        textView.setText(alter);

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

        databaseHelper = new MQTTDBHelper(view.getContext());
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from MQTT WHERE NAME = '" + title + "'", null);
        String name = null, finalValue = null, action_value = null;
        int id = 0, active = 0, dashboard = 0, subscribe = 0;
        userCursor.moveToFirst();
        name = userCursor.getString(userCursor.getColumnIndex("NAME"));
        finalValue = userCursor.getString(userCursor.getColumnIndex("VALUE"));
        action_value = userCursor.getString(userCursor.getColumnIndex("ACTION_VALUE"));
        id = userCursor.getInt(userCursor.getColumnIndex("_id"));
        active = userCursor.getInt(userCursor.getColumnIndex("ACTIVE"));
        dashboard = userCursor.getInt(userCursor.getColumnIndex("DASHBOARD"));
        subscribe = userCursor.getInt(userCursor.getColumnIndex("SUBSCRIBE"));

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter alter topic name");

        final EditText input = new EditText(this);
        alert.setView(input);

        final String finalAction_value = action_value;
        final int finalActive = active;
        final int finalDashboard = dashboard;
        final int finalSubscribe = subscribe;
        final int finalId = id;
        final String finalName = name;
        final String finalValue1 = finalValue;
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("ALTER_NAME", value);
                TextView tv = findViewById(R.id.textView4);
                String alter = tv.getText().toString();
                if (alter.equals("")) {
                    cv.put("ACTIVE", finalActive);
                    cv.put("DASHBOARD", finalDashboard);
                    cv.put("SUBSCRIBE", finalSubscribe);
                    cv.put("VALUE", finalValue1);
                    cv.put("_id", finalId);
                    cv.put("NAME", finalName);
                    cv.put("ACTION_VALUE", finalAction_value);
                    db.delete("MQTT", "NAME = '" + title + "'", null);
                    db.insert("MQTT", null, cv);
                }
                db.update("MQTT", cv, "NAME = ?", new String[]{title});

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(view.getContext(), TopicHelper.class));
            }
        });

        alert.show();
    }

    public void selectActions(final View view) {

        TextView textView = findViewById(R.id.title);
        final String title = textView.getText().toString();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose action");
        String[] items = {"Turn on", "Turn off", "Open", "Close"};
        databaseHelper = new MQTTDBHelper(view.getContext());
        final SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        final ContentValues cv = new ContentValues();
        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        cv.put("ACTION_VALUE", "Turn on");
                        sqLiteDatabase.update("MQTT", cv, "NAME = '" + title + "'", null);
                        break;
                    case 1:
                        cv.put("ACTION_VALUE", "Turn off");
                        sqLiteDatabase.update("MQTT", cv, "NAME = '" + title + "'", null);
                        break;
                    case 2:
                        cv.put("ACTION_VALUE", "Open");
                        sqLiteDatabase.update("MQTT", cv, "NAME = '" + title + "'", null);
                        break;
                    case 3:
                        cv.put("ACTION_VALUE", "Close");
                        sqLiteDatabase.update("MQTT", cv, "NAME = '" + title + "'", null);
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

    public void subscribe(View view) {

        db = databaseHelper.getReadableDatabase();
        TextView textView = findViewById(R.id.title);
        final String title = textView.getText().toString();
        ContentValues cv = new ContentValues();
        if (isSubscribe == 0) {
            cv.put("SUBSCRIBE", 1);
        } else {
            cv.put("SUBSCRIBE", 0);
        }
        db.update("MQTT", cv, "NAME = ?", new String[]{title});

    }
}
