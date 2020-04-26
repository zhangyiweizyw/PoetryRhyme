package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DakaActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_daka);

        findView();
        action();
    }

    private void findView(){
        back=findViewById(R.id.img_daka_back);
        done=findViewById(R.id.img_done);
    }
    private void action(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DakaActivity.this,DakaShareActivity.class));
            }
        });
    }
}
