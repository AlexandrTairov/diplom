package com.example.diplom.settings;

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
import androidx.annotation.Nullable;
import com.example.diplom.MQTTClient;
import com.example.diplom.R;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.database.UserDBHelper;
import com.example.diplom.topics.TopicsShowActivity;

import java.util.ArrayList;

public class MQTTSettings extends Activity {

    UserDBHelper userDBHelper;
    SQLiteDatabase database;
    int isConnect = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mqtt_settings);

        userDBHelper = new UserDBHelper(this);
        database = userDBHelper.getReadableDatabase();

        Cursor userCursor = database.rawQuery("select USERNAME from USER WHERE _id = 1", null);
        userCursor.moveToFirst();
        String username = null, password = null, address = null;
        try {
           username  = userCursor.getString(userCursor.getColumnIndex("USERNAME"));
        } catch (Exception e) {
            username = "";
        }

        userCursor = database.rawQuery("select PASSWORD from USER WHERE _id = 1", null);
        userCursor.moveToFirst();

        try {
            password  = userCursor.getString(userCursor.getColumnIndex("PASSWORD"));
        } catch (Exception e) {
            password = "";
        }

        userCursor = database.rawQuery("select ADDRESS from USER WHERE _id = 1", null);
        userCursor.moveToFirst();

        try {
            address  = userCursor.getString(userCursor.getColumnIndex("ADDRESS"));
        } catch (Exception e) {
            address = "Адрес MQTT брокера";
        }

        TextView textView = findViewById(R.id.username);
        textView.setText(username);

        textView = findViewById(R.id.passwordText);
        textView.setText(password);

        textView = findViewById(R.id.textView2);
        textView.setText(address);


        userCursor = database.rawQuery("select CONNECT_ON_LAUNCH from USER WHERE _id = 1", null);
        userCursor.moveToFirst();

        try {
            isConnect = userCursor.getInt(userCursor.getColumnIndex("CONNECT_ON_LAUNCH"));
        } catch (Exception e) {
            isConnect = 0;
        }

        Switch switchActive = findViewById(R.id.switch1);
        if (isConnect == 0) {
            switchActive.setChecked(false);
        } else {
            switchActive.setChecked(true);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        userDBHelper.close();
    }

    public void openAddressSettings(final View view) {

        UserDBHelper dbHelper = new UserDBHelper(view.getContext());
        database = dbHelper.getWritableDatabase();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Введите адрес подключения");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                TextView textView = findViewById(R.id.username);
                String name = textView.getText().toString();
                textView = findViewById(R.id.passwordText);
                String password = textView.getText().toString();
                cv.put("USERNAME", name);
                cv.put("PASSWORD", password);
                cv.put("ADDRESS", value);
                textView = findViewById(R.id.textView2);
                String address = textView.getText().toString();
                if (address.equals("Адрес MQTT брокера")) {
                    cv.put("_id", 1);
                    database.delete("USER", "_id = 1", null);
                    database.insert("USER", null ,cv);
                } else {
                    database.update("USER", cv, "_id = ?", new String[] {String.valueOf(1)});
                }

                Intent intent = new Intent(view.getContext(), MQTTSettings.class);
                startActivity(intent);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(view.getContext(), MQTTSettings.class));
            }
        });

        alert.show();

    }

    public void subscribeTopic(final View view) {

        Intent intent = new Intent(view.getContext(), TopicsShowActivity.class);
        startActivity(intent);
    }

    public void openUsernameSettings(final View view) {

        userDBHelper = new UserDBHelper(this);
        database = userDBHelper.getWritableDatabase();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Введите имя пользователя");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                TextView textView = findViewById(R.id.passwordText);
                String password = textView.getText().toString();
                textView = findViewById(R.id.textView2);
                String address = textView.getText().toString();
                cv.put("USERNAME", value);
                cv.put("PASSWORD", password);
                cv.put("ADDRESS", address);
                textView = findViewById(R.id.username);
                String name = textView.getText().toString();
                if (name.equals("")) {
                    cv.put("_id", 1);
                    database.delete("USER", "_id = 1", null);
                    database.insert("USER", null ,cv);
                } else {
                    database.update("USER", cv, "_id = ?", new String[] {String.valueOf(1)});
                }

                Intent intent = new Intent(view.getContext(), MQTTSettings.class);
                startActivity(intent);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(view.getContext(), MQTTSettings.class));
            }
        });

        alert.show();
    }

    public void openPasswordSettings(final View view) {

        userDBHelper = new UserDBHelper(this);
        database = userDBHelper.getWritableDatabase();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Введите пароль");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                TextView textView = findViewById(R.id.username);
                String name = textView.getText().toString();
                textView = findViewById(R.id.textView2);
                String address = textView.getText().toString();
                cv.put("USERNAME", name);
                cv.put("PASSWORD", value);
                cv.put("ADDRESS", address);
                textView = findViewById(R.id.passwordText);
                String password = textView.getText().toString();
                if (password.equals("")) {
                    cv.put("_id", 1);
                    database.delete("USER", "_id = 1", null);
                    database.insert("USER", null ,cv);
                } else {
                    database.update("USER", cv, "_id = ?", new String[] {String.valueOf(1)});
                }

                Intent intent = new Intent(view.getContext(), MQTTSettings.class);
                startActivity(intent);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(view.getContext(), MQTTSettings.class));
            }
        });

        alert.show();
    }

    public void connect(View view) {

//        TextView Text = findViewById(R.id.textView6);
//        String username = Text.getText().toString();
//        Text = findViewById(R.id.textView7);
//        String password = Text.getText().toString();
//        Text = findViewById(R.id.textView3);
//        String address = Text.getText().toString();
//
//        MQTTClient client = new MQTTClient();
//        MQTTDBHelper mqttdbHelper = new MQTTDBHelper(view.getContext());
//        database = mqttdbHelper.getReadableDatabase();
//        Cursor userCursor = database.rawQuery("select NAME, VALUE from MQTT WHERE SUBSCRIBE = 1", null);
//        userCursor.moveToFirst();
//        ArrayList<String> names = new ArrayList<>(), values = new ArrayList<>();
//        int count = 0;
//        names.add(userCursor.getString(userCursor.getColumnIndex("NAME")));
//        values.add(userCursor.getString(userCursor.getColumnIndex("VALUE")));
//        for (int i = 0; i < names.size(); i++) {
//            client.publishMessage(names.get(i), values.get(i), username, password, address);
//        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Успешно подключено");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void changeConnectOnLaunch(View view) {

        database = userDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (isConnect == 0) {
            cv.put("CONNECT_ON_LAUNCH", 1);
        } else {
            cv.put("CONNECT_ON_LAUNCH", 0);
        }
        database.update("USER", cv, "_id = ?", new String[]{String.valueOf(1)});
    }
}