package com.example.a15632.poetrydemo.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.Poetry;
import com.example.a15632.poetrydemo.Entity.User;
import com.example.a15632.poetrydemo.MainActivity;
import com.example.a15632.poetrydemo.R;
import com.google.gson.Gson;
import com.mob.MobSDK;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PhoneActivity extends AppCompatActivity {


    //  交互
    private OkHttpClient client;

    private TextView phone_msg;//按钮
    private EditText phone_new;
    private EditText phone_et_msg;
    private Button phone_btn;
    private SharedPreferences sharedPreferences;

    private String phone;
    private ImageView phone_login_back;
    //msg
    private String message;
    private EventHandler eh;
    private final static int PHONE_TRUE = 1;
    //主线程
    private final Handler mHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        client = new OkHttpClient();
        sharedPreferences = getSharedPreferences("loginInfo",
                MODE_PRIVATE);
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
                            msg.what = PHONE_TRUE;
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

        listener();

    }

    private void listener() {
        //当点击“发送验证码”时发出一个提示，并且开始倒计时
        phone_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PhoneActivity.this, "已发送", Toast.LENGTH_LONG).show();
                phone = phone_new.getText()+"";
                SMSSDK.getVerificationCode("86", phone);
                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        phone_msg.setEnabled(false);
                        phone_msg.setText("已发送（" + millisUntilFinished / 1000 + ")");
                    }

                    @Override
                    public void onFinish() {
                        phone_msg.setEnabled(true);
                        phone_msg.setText("重新获取验证码");

                    }
                }.start();
            }
        });

        //true
        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = phone_et_msg.getText().toString();
                Log.e("Message", message);
                SMSSDK.submitVerificationCode("86", phone, message);
            }
        });

        //返回
        phone_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void find() {
        phone_btn = findViewById(R.id.phone_btn);
        phone_msg =  findViewById(R.id.phone_msg);
        phone_et_msg = findViewById(R.id.phone_et_msg);
        phone_new = findViewById(R.id.phone_new);
        phone_login_back = findViewById(R.id.phone_login_back);

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
                case PHONE_TRUE:
                    //登陆成功
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phone",phone);
                    editor.commit();
                    changePhone();


                    Toast.makeText(getBaseContext(),"手机更换成功",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }

        }
    }

    private void changePhone() {
        Log.e("修改phone", "运行");
        String jsonStr = null;
        int id = sharedPreferences.getInt("id",0);
        Log.e("修改", phone);
        User user = new User(id,phone);
        jsonStr = new Gson().toJson(user);

        //2.创建RequestBody请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                jsonStr);
        //3.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "user/updatePhone")
                .post(body)//请求方式POST
                .build();
        //4.创建Call对象
        Call call = client.newCall(request);
        Log.e("change", "phone");
        //5.发送请求(可以同步或者异步)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("查询的诗句", "-" + jsonStr);
                Intent intent = new Intent(PhoneActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
