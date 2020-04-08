package com.example.a15632.poetrydemo;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_login);
        findView();
        action();
    }
    private void findView() {
        imageView=findViewById(R.id.img_login_back);
        textView=findViewById(R.id.tv_send);
    }
    private void action() {
        //返回
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //当点击“发送验证码”时发出一个提示，并且开始倒计时
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"已发送",Toast.LENGTH_LONG).show();

                CountDownTimer timer=new CountDownTimer(60*1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textView.setEnabled(false);
                        textView.setText("已发送（"+millisUntilFinished/1000+")");
                    }

                    @Override
                    public void onFinish() {
                        textView.setEnabled(true);
                        textView.setText("重新获取验证码");

                    }
                }.start();
            }
        });
    }


}
