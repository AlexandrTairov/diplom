package com.example.diplom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadReceiv extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, MainActivity.class);
        context.startActivity(serviceIntent);
    }
}
