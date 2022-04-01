package com.example.androidstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidstudy.db.Student;

import org.litepal.LitePal;

public class DataBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        SQLiteDatabase db = LitePal.getDatabase();
        initView();
    }

    private void initView() {
        findViewById(R.id.add_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setName("丁文高");
                student.setAge(29);
                student.setSex("男");
                boolean saved = student.save();
                if (saved) {
                    Toast.makeText(DataBaseActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}