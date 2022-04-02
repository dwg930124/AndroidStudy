package com.example.androidstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.example.androidstudy.service.MyService;
import com.example.androidstudy.service.interfaces.TestInterface;

public class ServiceActivity extends AppCompatActivity {

    private Intent mStartService;
    private MyServiceConnection mServiceConnection;
    private TestInterface mThirdService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartService = new Intent(ServiceActivity.this, MyService.class);
                startService(mStartService);
            }
        });

        findViewById(R.id.stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mStartService) {
                    stopService(mStartService);
                }
            }
        });

        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bindService = new Intent(ServiceActivity.this, MyService.class);
                mServiceConnection = new MyServiceConnection();
                bindService(bindService, mServiceConnection, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.unbind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mServiceConnection) {
                    unbindService(mServiceConnection);
                }
            }
        });

        findViewById(R.id.call_service_inner_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThirdService != null) {
                    mThirdService.testMethod();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mStartService) {
            stopService(mStartService);
        }

        if (null != mServiceConnection) {
            unbindService(mServiceConnection);
        }
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(ServiceActivity.this, "服务连接成功", Toast.LENGTH_SHORT).show();
            mThirdService = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(ServiceActivity.this, "服务连接失败", Toast.LENGTH_SHORT).show();
        }
    }
}