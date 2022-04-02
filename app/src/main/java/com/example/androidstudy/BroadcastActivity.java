package com.example.androidstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidstudy.broadcast.MyReceiver;

public class BroadcastActivity extends AppCompatActivity {

    private MyReceiver mMyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        initView();
    }

    private void initView() {
        findViewById(R.id.regisiter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("com.example.androidstudy.BROADCAST_TEST");
                mMyReceiver = new MyReceiver();
                registerReceiver(mMyReceiver, filter);
                Toast.makeText(BroadcastActivity.this, "动态注册成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mMyReceiver) {
            unregisterReceiver(mMyReceiver);
        }
    }
}