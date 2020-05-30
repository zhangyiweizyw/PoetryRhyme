package com.example.a15632.poetrydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.a15632.poetrydemo.Entity.GamePaiHangBang;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * json参数表
 * game_userId  自己的id
 * game_friendId 对方的id
 * game_problem 题目
 * game_key 相对应的答案
 * game_my_anwser 我的回答
 * game_other_anwser 对方的回答
 */
public class DoublePlayActivity extends AppCompatActivity {
    //等待加载框
    private static AlertDialog alertDialog;
    private static AlertDialog alertDialog_waiting_data;
    //网络相关
    private static final int CHANGE = 1;
    private static final int CUT_ALERT = 2;
    private static final int CHANGE_MY_SCORE=3;
    private static final String IP = "192.168.0.104";
    private static final int PORT = 4321;
    private Socket clientSocket;
    private int userId = 21;//模拟已登录的用户的id
    private int friendId;
    private String friendID;

    private BufferedReader reader;
    private BufferedWriter writer;

    //ui部分
    private TextView player1_id;
    private TextView player2_id;

    private TextView player1_name;
    private TextView player2_name;

    private TextView my_score;
    private static int mScore=0;
    private TextView other_score;
    private static int oScore=0;

    private EditText your_anwser;
    private Button btn;

    private TextView sum;//题目总数
    private int sums=8;

    private TextView daojishi2;

    //题目相关
    private TextView problem;
    private String key;

    //对方的回答
    private String otherAnwser;

    //倒计时
    private static MyCountDown mCountDown;

    //音乐播放
    private MediaPlayer music;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE: {
                    try {
                        String message = (String) msg.obj;
                        JSONObject jsonObject = new JSONObject(message);
                        if (jsonObject.has("game_friendId")) {
                            Log.e("成功", "接收到了对方的id");
                            //开始更新玩家ui
                            //模拟更新UI
                            player1_id.setText(userId + "");
                            player2_id.setText(jsonObject.getString("game_friendId"));

                            if (userId == 19) {
                                player1_name.setText("花满楼");
                                player2_name.setText("叶孤城");
                            } else if (userId == 21) {
                                player2_name.setText("花满楼");
                                player1_name.setText("叶孤城");
                            }
                        } else if (jsonObject.has("game_problem")) {
                            Log.e("成功", "接收到了题目");
                            if(sums>0){
                                loadingProblem();
                                mCountDown=new MyCountDown(20*1000,1000);
                                mCountDown.start();
                                problem.setText(jsonObject.getString("game_problem").toString());
                                key = jsonObject.getString("game_key");
                                sum.setText(sums--+"");
                            }else {
                                Log.e("嗷","两人答题已结束");
                            }

                        } else if (jsonObject.has("game_other_anwser")) {
                            Log.e("成功", "接收到了对方的答案");
                            otherAnwser=jsonObject.getString("game_other_anwser");
                            if(key.isEmpty()==false){
                                if(key.equals(otherAnwser)==true){
                                    Log.e("哦豁","对方的回答正确了");
                                    ++oScore;
                                    other_score.setText(oScore+"");
                                }else {
                                    Log.e("欸嘿","对方的回答是错误的");
                                }
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }
                case CUT_ALERT: {
                    alertDialog.dismiss();
                    break;
                }
                case CHANGE_MY_SCORE:{
                    ++mScore;
                    my_score.setText(mScore+"");

                    break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_double_play);
        initView();
        loadingGame();//dialog
        initSocket();
        aciton();

    }

    private void aciton() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDown.cancel();

                sendAnwser();
                String anwser=your_anwser.getText().toString();
                if(key.equals(anwser)){
                    Log.e("成功！","我的回答是正确的！");
                    changMyScore();
                    alertSuccess();

                }else{
                    Log.e("啊哦！","我的回答有误");
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
        final android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(DoublePlayActivity.this);
        builder.setTitle("回答正确！(。・∀・)ノ");
        builder.setMessage("等待对面玩家完成答题中。。");


        final android.support.v7.app.AlertDialog dialog=builder.create();
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },500);
    }
    public void alertFail(){
        playSound(R.raw.game_fail_sound);
        final android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(DoublePlayActivity.this);
        builder.setTitle("回答有误！QAQ");
        builder.setMessage("等待对面玩家完成答题中。。");

        final android.support.v7.app.AlertDialog dialog=builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },500);

    }
    public void changMyScore(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(200);
                    Message msg=new Message();
                    msg.what=CHANGE_MY_SCORE;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 将发送的消息，传给服务端然后传给连接那方
     */
    public void sendAnwser() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    Log.e("成功！", "准备发送我的答案");
                    writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8"));
                    String anwser = your_anwser.getText().toString();
                    Log.e("准备发送的内容是：", anwser);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("game_my_anwser", anwser);
                    int s = 2;
                    while (s-- > 0) {
                        writer.write(jsonObject.toString() + "\n");
                        writer.flush();
                    }

                    Log.e("结束", "发送题目结束");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //双人游戏开始，，
    private void doublePlayBegin() {

    }

    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 第一次连接时发送我方id
     */
    private void sendID() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8"));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("game_userId", userId);
            writer.write(jsonObject.toString() + "\n");
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接服务端，并把自己ip传给服务端进行登记
     */
    private void initSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    clientSocket = new Socket(IP, PORT);
                    reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "utf-8"));
                    writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8"));
                    sendID();
                    while (true) {
                        if (reader.ready()) {
                            dissmissAlert();


                            Message msg = handler.obtainMessage();
                            msg.what = CHANGE;
                            msg.obj = reader.readLine();
                            Log.e("最开始时接受到了：", (String) msg.obj);
                            handler.sendMessage(msg);
                        }
                        Thread.sleep(50);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void dissmissAlert() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(100);
                    Message msg = new Message();
                    msg.what = CUT_ALERT;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        player1_id = findViewById(R.id.tv_player1);
        player2_id = findViewById(R.id.tv_player2);
        player1_name = findViewById(R.id.tv_player1_name);
        player2_name = findViewById(R.id.tv_player2_name);

        my_score=findViewById(R.id.tv_double_game_my_score);
        other_score=findViewById(R.id.tv_double_game_other_score);

        problem = findViewById(R.id.tv_doubl_play_problem);

        your_anwser = findViewById(R.id.et_double_game_et);
        btn = findViewById(R.id.btn_double_game_btn);

        sum=findViewById(R.id.tv_double_game_problem_sum);

        daojishi2=findViewById(R.id.tv_double_game_daojishi);
    }

    /**
     * 等待匹配的dialog
     */
    public void loadingGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DoublePlayActivity.this);
        View view = getLayoutInflater().inflate(R.layout.loading_play, null);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();


    }

    /**
     * 加载题目dialog
     */
    public void loadingProblem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DoublePlayActivity.this);
        View view = getLayoutInflater().inflate(R.layout.loading_problem, null);

        builder.setView(view);
        alertDialog_waiting_data = builder.create();
        alertDialog_waiting_data.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog_waiting_data.dismiss();
            }
        },1000);


    }


    /**
     * 倒计时class
     */
    private  class MyCountDown extends CountDownTimer {

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
            daojishi2.setEnabled(false);
            daojishi2.setText(millisUntilFinished/1000+"");
        }

        @Override
        public void onFinish() {


        }
    }
}
