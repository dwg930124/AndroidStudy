package com.example.androidstudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mContextMenu;
    private Button mAdb;
    private EditText mAccount;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: ");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mContextMenu = findViewById(R.id.context_menu);
        mContextMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                registerForContextMenu(view);   // context menu
                openContextMenu(view);
                return true;
            }
        });

        mAdb = findViewById(R.id.adb);
        mAdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdbActivity.class);
                startActivity(intent);
            }
        });

        mAccount = findViewById(R.id.account);
        mPassword = findViewById(R.id.password);
        Switch aSwitch = findViewById(R.id.switch_info);
        Button saveToApp = findViewById(R.id.info_save_app);
        Button saveToSp = findViewById(R.id.info_save_sp);

        // 保存到App
        saveToApp.setOnClickListener(new View.OnClickListener() {

            private FileOutputStream mFos;

            @Override
            public void onClick(View view) {
                String accountText = mAccount.getText().toString().trim();
                String passwordText = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(accountText) || TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(MainActivity.this, "账户密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    mFos = openFileOutput("user_info.txt", MODE_PRIVATE);
                    mFos.write((accountText + "***" + passwordText).getBytes());
                    Toast.makeText(MainActivity.this, "保存完成", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (mFos != null) {
                        try {
                            mFos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        // 保存到SharePreference
        saveToSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = aSwitch.isChecked();
                SharedPreferences sp = getSharedPreferences("user_setup", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("save_password", checked);
                edit.apply();
                Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

        // 回显数据
        findViewById(R.id.display_all).setOnClickListener(new View.OnClickListener() {

            private BufferedReader mReader;

            @Override
            public void onClick(View v) {
                // 回显账户、密码
                try {
                    FileInputStream fis = openFileInput("user_info.txt");
                    mReader = new BufferedReader(new InputStreamReader(fis));
                    String userInfo = mReader.readLine();
                    String[] split = userInfo.split("\\*\\*\\*");
                    mAccount.setText(split[0]);
                    mPassword.setText(split[1]);
                    Toast.makeText(MainActivity.this, "回显完成", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
//                    Log.e(TAG, "try catch finally");
                    if (mReader != null) {
                        try {
                            mReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // 回显用户配置
                SharedPreferences userSetup = getSharedPreferences("user_setup", MODE_PRIVATE);
                boolean checked = userSetup.getBoolean("save_password", false);
                aSwitch.setChecked(checked);
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }

        });

        findViewById(R.id.database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DataBaseActivity.class));
            }
        });

        findViewById(R.id.skip1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                startActivity(intent);
            }
        });

        findViewById(R.id.skip2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.androidstudy.ACTION_START");
                intent.setPackage("com.example.androidstudy");
                startActivity(intent);
            }
        });

        findViewById(R.id.again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "复制");
        menu.add(0, 2, 0, "剪切");
        menu.add(0, 3, 0, "粘贴");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case 1:
                Toast.makeText(this, "点击了：复制", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                mContextMenu.setText("");
                Toast.makeText(this, "点击了：剪切", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                mContextMenu.setText("这是粘贴的内容");
                Toast.makeText(this, "点击了：粘贴", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}