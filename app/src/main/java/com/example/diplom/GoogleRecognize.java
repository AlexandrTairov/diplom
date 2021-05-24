package com.example.diplom;

import android.content.Intent;
import android.speech.RecognizerIntent;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GoogleRecognize {

    public void googleRecognize() {

    }

    public Intent getIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        return intent;
    }
}
