package com.example.diplom.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.diplom.database.DBClear;
import com.example.diplom.R;
import com.example.diplom.topics.TopicsAddActivity;
import com.example.diplom.topics.TopicsShowActivity;

public class GeneralSettings extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_settings);
    }

    public void back(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void setupTopics(View view) {
        Intent intent = new Intent(this, TopicsAddActivity.class);
        startActivity(intent);
    }

    public void clearDatabase(View view) {
        Intent intent = new Intent(this, DBClear.class);
        startActivity(intent);
    }

    public void showTopics(View view) {
        Intent intent = new Intent(this, TopicsShowActivity.class);
        startActivity(intent);
    }
}
