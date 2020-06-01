package com.example.a15632.poetrydemo.drawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a15632.poetrydemo.R;

public class PasswordChooseActivity extends AppCompatActivity {

    private Button choose_pwd;
    private Button choose_phone;
    private ImageView password_choose_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_password_choose);
        choose_phone = findViewById(R.id.choose_phone);
        choose_pwd  = findViewById(R.id.choose_pwd);
        password_choose_back = findViewById(R.id.password_choose_back);



        choose_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChooseActivity.this,CheckActivity.class);
                intent.putExtra("next","password");
                startActivity(intent);
            }
        });

        choose_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChooseActivity.this,PasswordActivity.class);
                intent.putExtra("choose","password");
                startActivity(intent);
            }
        });

        password_choose_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
