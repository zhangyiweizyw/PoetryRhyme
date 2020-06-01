package com.example.a15632.poetrydemo;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button done;
    private EditText anwser;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private String my_problem;
    private String my_key;//答案

    private MediaPlayer music;

    private TextView problem_num;
    private int pn;

    private final static int CHANGEPROBLEM=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHANGEPROBLEM:{
                    if(my_problem.isEmpty()!=true&&my_problem.length()>0){
                        problem.setText(my_problem);
                        anwser.setText("");
                        problem_num.setText(pn+"");
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
        done=findViewById(R.id.btn_game_done);

        problem_num=findViewById(R.id.tv_game_problem_num);

        pn=Integer.parseInt(problem_num.getText().toString());

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
        final MyCountDown myCountDown=new MyCountDown(20*1000,1000);
        myCountDown.start();
        //4.判断对错
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDown.cancel();
                if(anwser.getText().toString().equals(my_key)){
                    alertSuccess();
                    Log.e("成功！","回答正确！！");
                }else
                {
                    Log.e("失败！","回答错误！！");
                    alertFail();
                }

            }
        });

    }
    public void playSound(int MusicId){
        music=MediaPlayer.create(this,MusicId);
        music.start();
    }
    public void alertSuccess(){
        playSound(R.raw.game_success_sound);
        final AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("回答正确！(。・∀・)ノ");
        builder.setPositiveButton("下一题", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GameActivity.this,"下一题",Toast.LENGTH_SHORT).show();
                pn=pn+1;
                gameBegin();
            }
        });
        builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GameActivity.this,"取消",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    public void alertFail(){
        playSound(R.raw.game_fail_sound);
        final AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
        builder.setTitle("很遗憾，回答错误QAQ");
        builder.setMessage("正确答案是： "+my_key);
        builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
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

            }
        }.start();
    }
    private class MyCountDown extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            daojishi.setEnabled(false);
            daojishi.setText(millisUntilFinished/1000+"");
        }

        @Override
        public void onFinish() {
            alertFail();
        }
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
