package com.example.a15632.poetrydemo;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {


    private TextView daojishi;
    private ImageView correct_img;
    private ImageView incorrect_img;

    private LinearLayout A;
    private LinearLayout B;
    private LinearLayout C;
    private LinearLayout D;

    private int anwser=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_game);
        findView();
        action();
    }


    private void findView() {
        daojishi=findViewById(R.id.tv_game_daojishi);
        correct_img=findViewById(R.id.img_game_correct);
        incorrect_img=findViewById(R.id.img_game_incorrect);
        A=findViewById(R.id.linear_A);
        B=findViewById(R.id.linear_B);
        C=findViewById(R.id.linear_C);
        D=findViewById(R.id.linear_D);

    }
    private void action() {
        countDown();
        //初始化异步任务
        final MyAsyncTask myAsyncTask=new MyAsyncTask();

       //设置点击事件
       A.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               anwser=1;
               myAsyncTask.execute();
           }
       });
       B.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               anwser=0;
               myAsyncTask.execute();
           }
       });
       C.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               anwser=0;
               myAsyncTask.execute();
           }
       });
       D.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               anwser=0;
               myAsyncTask.execute();
           }
       });
    }

    private void countDown(){
        //倒计时
        CountDownTimer timer=new CountDownTimer(15*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                daojishi.setEnabled(false);
                daojishi.setText(millisUntilFinished/1000+"");
            }

            @Override
            public void onFinish() {
                daojishi.setEnabled(true);
                anwser=0;
                MyAsyncTask myAsyncTask=new MyAsyncTask();
                myAsyncTask.execute();

            }
        }.start();
    }
    private class MyAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            switch (anwser){
                case 1:{
                    daojishi.setVisibility(View.GONE);
                    correct_img.setVisibility(View.VISIBLE);
                    break;
                }
                case  0:{
                    daojishi.setVisibility(View.GONE);
                    incorrect_img.setVisibility(View.VISIBLE);
                    break;
                }
            }
            super.onProgressUpdate(values);
        }
    }
}
