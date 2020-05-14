package com.example.a15632.poetrydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DakaShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_daka_share);
    }
}
