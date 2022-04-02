package com.example.androidstudy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.example.androidstudy.BROADCAST_TEST".equals(action)) {
            Toast.makeText(context, "接收到广播", Toast.LENGTH_SHORT).show();
        }
    }
}