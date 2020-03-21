package com.example.a15632.poetrydemo;

import android.support.v7.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Splash extends AppCompatActivity {
    //每次开机时调用的诗句，先判断开没开网络 再进行，否则就不调用
    private final String getPoetryJson = "https://v1.jinrishici.com/all.json";
    private String firstJson;//得到的json数
    private String allPoetry;//解析json数据得到的文字
    private String[] poetry=new String[4];
    private String firstPoetry;//拆分后的第一句
    private String secondPoetry;//拆分后的第二句
    private String ThirdPoetry;//拆分后的第三句（如果可能的话~）
    private Gson gson = new Gson();
    private static final int POETRY_UPDATE=1;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private TextView textViewPoetryEveryday;
    private TextView textViewPoetryEveryday2;
    private TextView textViewPoetryEveryday3;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case POETRY_UPDATE:{
                    textViewPoetryEveryday.setText(firstPoetry);
                    textViewPoetryEveryday2.setText(secondPoetry);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏幕展示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置动画
        getJson();
        setContentView(R.layout.activity_splash);

        //设置字体
        textViewPoetryEveryday = findViewById(R.id.tv_poetry_everyday);
        textViewPoetryEveryday2 = findViewById(R.id.tv_poetry_everyday2);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontdemo.TTF");
//        textViewPoetryEveryday.setTypeface(typeface);
//        textViewPoetryEveryday2.setTypeface(typeface);
        //设置间隔
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3500);
                    //跳转页面
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

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

    private void getPoetry(String json) {
        //判空 似乎没必要QAQ
        if (json == null || json.trim().length() == 0) {
            Log.e("wtf", "原先的json为空");
        } else {
            try {
                JSONObject jsonObject = new JSONObject(json);
                allPoetry = jsonObject.getString("content");
                Log.e("得到的诗句为： ", allPoetry);
                splitSentence(allPoetry);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 根据标点符号进行句子拆分，并且保留句子结尾符号
     */
    public void splitSentence(String cmt) {
        /*正则表达式：句子结束符*/
        String regEx = ",|\\.|\\?|!|:|;|~|，|：|。|！|；|？| ";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(cmt);
        /*按照句子结束符分割句子*/
        String[] words = p.split(cmt);
        /*将句子结束符连接到相应的句子后*/
        if (words.length > 0) {
            int count = 0;
            while (count < words.length) {
                if (m.find()) {
                    words[count] += m.group();
                }
                count++;
            }
        }
        firstPoetry=words[0];
        secondPoetry=words[1];
        Log.e("这两句诗是：",firstPoetry+""+secondPoetry);
        updatePoetry();
    }
    private void updatePoetry(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Message msg=new Message();
                msg.what=POETRY_UPDATE;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
