package com.example.diplom.database;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.diplom.R;
import com.example.diplom.settings.GeneralSettings;

public class DBClear extends Activity implements View.OnClickListener {

    Button btnClear;

    DBHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_clear_database);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        databaseHelper = new DBHelper(this);
    }

    public void back(View view) {
        Intent intent = new Intent(this, GeneralSettings.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Log.d("myLogs", "--- Clear mqtt: ---");
        int clearCount = db.delete("mqtt", null, null);
        Log.d("myLogs", "deleted rows count = " + clearCount);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Database was cleared");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }
}
