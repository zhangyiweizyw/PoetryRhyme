package com.example.a15632.poetrydemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Author;
import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.MobSDK;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    //one
    private EditText et_phone;
    private TextView tv_msg;
    private EditText et_msg;
    private Button btn_login_msg;
    private EventHandler eh;
    private TextView login_switch;
    private LinearLayout layout_one;
    private LinearLayout layout_two;
    private final Handler mHandler = new MyHandler();
    private final static int MSGWHAT = 3;
    //tow
    private EditText et_name;
    private EditText et_pwd;
    private Button btn_login_name;
    private String name;
    private String pwd;
    //
    private final static int ONE = 1;
    private final static int TWO = 2;
    //手机号
    private String phone;
    //验证码
    private String message;
    //交互
    private OkHttpClient client;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_login);
        findView();
        //用户同意
        MobSDK.submitPolicyGrantResult(true, null);
        //从短信自动获取
        SMSSDK.setAskPermisionOnReadContact(true);
        //Handler
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                // TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Log.e("EventHandler", "回调完成");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e("EventHandler", "提交验证码成功");
                        post(ONE);

                        Message msg = new Message();
                        msg.what = MSGWHAT;
                        msg.arg1 = event;
                        msg.arg2 = result;
                        msg.obj = data;
                        mHandler.sendMessage(msg);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.e("EventHandler", "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.e("EventHandler", "返回支持发送验证码的国家列表");
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }

            }
        };

        //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh);
        //设置点击事件
        action();

        client = new OkHttpClient();

        sharedPreferences = getSharedPreferences("loginInfo",
                MODE_PRIVATE);//只在本程序内部访问
    }

    private void findView() {
        title = findViewById(R.id.login_title);
        back = findViewById(R.id.login_back);
        et_phone = findViewById(R.id.et_phone);
        tv_msg = findViewById(R.id.tv_msg);
        et_msg = findViewById(R.id.et_msg);
        btn_login_msg = findViewById(R.id.btn_login_phone);
        login_switch = findViewById(R.id.login_switch);
        layout_one = findViewById(R.id.layout_one);
        layout_two = findViewById(R.id.layout_two);

        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login_name = findViewById(R.id.btn_login_name);
    }

    private void action() {
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //当点击“发送验证码”时发出一个提示，并且开始倒计时
        tv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone.getText().toString();
                Toast.makeText(LoginActivity.this, "已发送", Toast.LENGTH_LONG).show();
                SMSSDK.getVerificationCode("86", phone);
                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        tv_msg.setEnabled(false);
                        tv_msg.setText("已发送（" + millisUntilFinished / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        tv_msg.setEnabled(true);
                        tv_msg.setText("重新获取验证码");

                    }
                }.start();
            }
        });

        btn_login_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = et_msg.getText().toString();
                Log.e("Message", message);
                SMSSDK.submitVerificationCode("86", phone, message);
            }
        });

        login_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().equals("密码登陆")){
                    switchOne();
                }else{
                    switchTwo();
                }
            }
        });

        btn_login_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                pwd = et_pwd.getText().toString();
                post(TWO);
            }
        });
    }

    //显示第一面
    public void switchOne(){

        Message msg = new Message();
        msg.what = ONE;
        mHandler.sendMessage(msg);
    }


    //显示第二面
    public void switchTwo(){
        Message msg = new Message();
        msg.what = TWO;
        mHandler.sendMessage(msg);
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSGWHAT:
                    //登陆成功

                    break;
                case ONE:
                    title.setText("手机号登录/注册");
                    login_switch.setText("密码登陆");
                    layout_one.setVisibility(View.VISIBLE);
                    layout_two.setVisibility(View.GONE);
                    break;
                case TWO:
                    title.setText("密码登陆");
                    login_switch.setText("短信登陆");
                    layout_one.setVisibility(View.GONE);
                    layout_two.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;

            }

        }
    }

    //注册、登陆成功后
    private void post(int type) {

        Log.e("post", "运行");
        String jsonStr = null;
        String ip = null;
        final User user = new User();
        if(type==ONE){
            user.setName(phone);
            user.setPhone(phone);
            ip = Constant.lcIp + "user/register";
        }else {
            user.setName(name);
            user.setPassword(pwd);
            ip = Constant.lcIp + "user/load";
        }

        jsonStr = new Gson().toJson(user);
        Log.e("发送User",jsonStr);
        //2.创建RequestBody请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                jsonStr);

        //3.创建Request对象

        Request request = new Request.Builder()
                .url(ip)
                .post(body)//请求方式POST
                .build();
        //4.创建Call对象
        Call call = client.newCall(request);
        Log.e("开启", "运行前");
        //5.发送请求(可以同步或者异步)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("结果", "-" + jsonStr);
                if(jsonStr.equals(null)||jsonStr.equals("")){
                    Log.e("登陆","null");

                }else {
                    User user1 = new Gson().fromJson(jsonStr, User.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id",user1.getId());
                    editor.putString("name",user1.getName()+"");
                    editor.putString("phone",user1.getPhone());
                    editor.putString("password",user1.getPassword()+"");
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }
        });
    }


}
