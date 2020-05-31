package com.example.a15632.poetrydemo.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.LoginActivity;
import com.example.a15632.poetrydemo.R;
import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class CheckActivity extends AppCompatActivity {

    private TextView check_phone;
    private TextView check_msg;//按钮
    private EditText check_et;
    private Button check_next;
    //user
    private SharedPreferences sharedPreferences;
    private String phone;
    private String unReadPhone;
    //msg
    private String message;
    private EventHandler eh;
    //主线程
    private final Handler mHandler = new MyHandler();
    private final static int CHECK_PHONE_NEXT = 1;
    private final static int CHECK_PWD_NEXT = 2;
    private String origin_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent = getIntent();
        origin_activity = intent.getStringExtra("next");
        find();
        //短信
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
                        Message msg = new Message();
                        if(origin_activity.equals("phone")){
                            msg.what = CHECK_PHONE_NEXT;
                        }else{
                            msg.what = CHECK_PWD_NEXT;
                        }

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
                    Toast.makeText(getBaseContext(),"验证码输入错误",Toast.LENGTH_SHORT).show();
                }

            }
        };

        //注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh);
        //获取phone
        sharedPreferences = getSharedPreferences("loginInfo",
                MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", "");
        unReadPhone = phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
        check_phone.setText(unReadPhone);

        listener();
    }

    private void listener() {
        //当点击“发送验证码”时发出一个提示，并且开始倒计时
        check_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CheckActivity.this, "已发送", Toast.LENGTH_LONG).show();
                SMSSDK.getVerificationCode("86", phone);
                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        check_msg.setEnabled(false);
                        check_msg.setText("已发送（" + millisUntilFinished / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        check_msg.setEnabled(true);
                        check_msg.setText("重新获取验证码");

                    }
                }.start();
            }
        });

        //next
        check_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = check_et.getText().toString();
                Log.e("Message", message);
                SMSSDK.submitVerificationCode("86", phone, message);
            }
        });

    }

    private void find() {
        check_phone = findViewById(R.id.check_phone);
        check_et = findViewById(R.id.check_et);
        check_msg = findViewById(R.id.check_msg);
        check_next = findViewById(R.id.check_btn);
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
    //主线程处理
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_PHONE_NEXT:
                    //登陆成功
                    Intent intent = new Intent(CheckActivity.this,PhoneActivity.class);
                    startActivity(intent);
                    break;
                case CHECK_PWD_NEXT:
                    Intent intent1 = new Intent(CheckActivity.this,PasswordActivity.class);
                    startActivity(intent1);
                    break;
                default:
                    break;

            }

        }
    }

}
