package com.example.diplom.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.diplom.R;
import com.example.diplom.database.UserDBHelper;
import com.example.diplom.topics.TopicsShowActivity;

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

    public void openAddressSettings(View view) {
    }

    public void subscribeTopic(View view) {
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
}
