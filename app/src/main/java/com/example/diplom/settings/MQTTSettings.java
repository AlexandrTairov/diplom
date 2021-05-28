package com.example.diplom.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.diplom.MQTTClient;
import com.example.diplom.MqttSign;
import com.example.diplom.R;
import com.example.diplom.database.AddressDBHelper;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.database.UserDBHelper;
import com.example.diplom.topics.TopicsShowActivity;

import java.util.ArrayList;

public class MQTTSettings extends Activity {

    UserDBHelper userDBHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mqtt_settings);
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

        AddressDBHelper addressDBHelper = new AddressDBHelper(this);
        database = addressDBHelper.getWritableDatabase();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter address to connect");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("ADDRESS", value);
                database.insert("ADDRESS", null ,cv);

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
        alert.setMessage("Enter username");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("USERNAME", value);
                database.insert("USER", null ,cv);

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
        alert.setMessage("Enter Password");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String value = input.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("PASSWORD", value);
                database.insert("USER", null ,cv);

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

        TextView Text = findViewById(R.id.textView6);
        String username = Text.getText().toString();
        Text = findViewById(R.id.textView7);
        String password = Text.getText().toString();
        Text = findViewById(R.id.textView3);
        String address = Text.getText().toString();

        MQTTClient client = new MQTTClient();
        MQTTDBHelper mqttdbHelper = new MQTTDBHelper(view.getContext());
        database = mqttdbHelper.getReadableDatabase();
        Cursor userCursor = database.rawQuery("select NAME, VALUE from MQTT WHERE SUBSCRIBE = 1", null);
        userCursor.moveToFirst();
        ArrayList<String> names = new ArrayList<>(), values = new ArrayList<>();
        int count = 0;
        names.add(userCursor.getString(userCursor.getColumnIndex("NAME")));
        values.add(userCursor.getString(userCursor.getColumnIndex("VALUE")));
        for (int i = 0; i < names.size(); i++) {
            client.publishMessage(names.get(i), values.get(i), username, password, address);
        }
    }
}
