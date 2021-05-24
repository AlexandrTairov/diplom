package com.example.diplom.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.diplom.R;

public class RecognizeSettings extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognize_settings);
    }

    public void back(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
