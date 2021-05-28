package com.example.diplom.topics;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.diplom.database.MQTTDBHelper;
import com.example.diplom.R;
import com.example.diplom.settings.GeneralSettings;

public class TopicsAddActivity extends Activity implements View.OnClickListener {

    Button btnAdd;

    MQTTDBHelper databaseHelper;

    EditText etName, etValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_topic);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etValue = findViewById(R.id.etValue);

        databaseHelper = new MQTTDBHelper(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, GeneralSettings.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        ContentValues cv = new ContentValues();

        String name = etName.getText().toString();
        String value = etValue.getText().toString();

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        cv.put("name", name);
        cv.put("value", value);

        long rowID = db.insert("mqtt", null, cv);
        Log.d("myLogs", "row inserted, ID = " + rowID);
        databaseHelper.close();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Added new topic!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}


