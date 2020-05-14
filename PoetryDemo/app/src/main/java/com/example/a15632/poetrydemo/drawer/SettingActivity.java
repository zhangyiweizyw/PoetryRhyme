package com.example.a15632.poetrydemo.drawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a15632.poetrydemo.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_setting);
    }
}
