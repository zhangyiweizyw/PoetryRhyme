package com.example.a15632.poetrydemo;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameActivity extends AppCompatActivity {
    private final String getPoetryJson = "https://v1.jinrishici.com/all.json";
    private String firstJson;//第一次得到的json数
    private String allPoetry;//解析json数据得到的文字
    private ImageView back;
    private TextView daojishi;
    private ImageView correct_img;
    private ImageView incorrect_img;
    private TextView problem;
    private EditText anwser;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private String my_problem;
    private String my_key;//答案

    private final static int CHANGEPROBLEM=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHANGEPROBLEM:{
                    if(my_problem.length()>0){
                        problem.setText(my_problem);
                    }
                    break;
                }
            }
        }
    };


    private int current=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_game);
        findView();
        action();
    }


    private void findView() {
        back=findViewById(R.id.img_game_back2);
        daojishi=findViewById(R.id.tv_game_daojishi);
        correct_img=findViewById(R.id.img_game_correct);
        incorrect_img=findViewById(R.id.img_game_incorrect);
        problem=findViewById(R.id.tv_game_problem);
        anwser=findViewById(R.id.et_game_you_anwser);

    }
    private void action() {

        //点击返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //题目相关
        gameBegin();


    }

    /**
     * 游戏开始，每一次游戏开始都会由这里开始
     */
    private void gameBegin(){
        //1.获取题目.处理题目
        getJson();
        //2,更新题目ui
        changeProblem();
        //3.开始倒计时
        countDown();
        //4.判断对错

    }

    private void changeProblem() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.what=CHANGEPROBLEM;
                handler.sendMessage(msg);
            }
        }.start();
    }

    /**
     * 获取题目json数据
     */
    private void getJson() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    MediaType type = MediaType.parse("application/json;charset=UTF-8");
                    Request request = new Request.Builder()
                            .url(getPoetryJson)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //成功了后就提取诗句出来
                        firstJson = response.body().string();
                        Log.e("获取到的json字符串是：", firstJson);
                        getPoetry(firstJson);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 解析出正常诗句
     */
    private void getPoetry(String json) {
        //判空 似乎没必要QAQ
        if (json == null || json.trim().length() == 0) {
            Log.e("wtf", "原先的json为空");
        } else {
            try {
                JSONObject jsonObject = new JSONObject(json);
                allPoetry = jsonObject.getString("content");
                Log.e("得到的诗句为： ", allPoetry);
                getProblem(allPoetry);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     *解析得到的诗句，然后弄成残缺的题目，并把题目赋给my_problem 答案赋给my_key以备用
     */
    private void getProblem(String allPoetry){
        if(allPoetry.length()>0){
            Random random=new Random();
            char[] charArray=allPoetry.toCharArray();
            while(true){
                int randnum=random.nextInt(allPoetry.length());
                String tt=String.valueOf(charArray[randnum]);
                //如果不等于就可以进行下一步
                if(tt.equals("，")==false&&tt.equals("。")==false){

                    Log.e("获得的随机数是: ",randnum+"");
                    Log.e("随机摘取的字是: ",charArray[randnum]+"");
                    my_key=charArray[randnum]+"";
                    Log.e("my_key答案等于: ",my_key);
                    charArray[randnum]='_';
                    my_problem=new String(charArray);
                    Log.e("my_problem等于： ",my_problem);
                    break;
                }

            }
        }else{
            Log.e("错误！！！","未正确获得诗词！");
        }
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
                current=0;
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
            switch (current){
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
