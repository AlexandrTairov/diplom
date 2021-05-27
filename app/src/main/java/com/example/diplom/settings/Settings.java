package com.example.diplom.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.diplom.MainActivity;
import com.example.diplom.R;

public class Settings extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void openGeneralSettings(View view) {
        Intent intent = new Intent(this, GeneralSettings.class);
        startActivity(intent);
    }

    public void openMQTTSettings(View view) {
        Intent intent = new Intent(this, MQTTSettings.class);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
