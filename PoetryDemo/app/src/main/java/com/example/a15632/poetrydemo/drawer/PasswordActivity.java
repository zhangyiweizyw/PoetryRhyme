package com.example.a15632.poetrydemo.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.User;
import com.example.a15632.poetrydemo.MainActivity;
import com.example.a15632.poetrydemo.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PasswordActivity extends AppCompatActivity {
    private LinearLayout layout;
    private EditText oldPwd;
    private EditText newPwd;
    private EditText newPwdTrue;
    private Button pwdBtn;
    private String pass = "";
    private ImageView password_back;

    private SharedPreferences sharedPreferences;

    private String pwd;
    private String old_pwd;
    private String new_pwd;
    private String true_pwd;
    //  交互
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_password);
        client = new OkHttpClient();
        find();
        sharedPreferences = getSharedPreferences("loginInfo",
                MODE_PRIVATE);
        pwd = sharedPreferences.getString("password","");
        Intent intent = getIntent();
        pass = intent.getStringExtra("choose");
        if(pass.equals("password")){
            layout.setVisibility(View.VISIBLE);

        }




        pwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pwd = newPwd.getText()+"";
                true_pwd = newPwdTrue.getText()+"";
                if(pass.equals("password")){
                    old_pwd = oldPwd.getText()+"";
                }
                if(new_pwd.equals(true_pwd) && old_pwd.equals(pwd)){
                    //  交互
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password",pwd);
                    editor.commit();
                    changePhone();
                }else if(!new_pwd.equals(true_pwd)){
                    Toast.makeText(getBaseContext(),"两次输入新密码不同！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"旧密码输入错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        password_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void find() {
        oldPwd = findViewById(R.id.pwd_old);
        newPwdTrue = findViewById(R.id.pwd_new_true);
        newPwd = findViewById(R.id.pwd_new);
        pwdBtn = findViewById(R.id.pwd_btn);
        layout = findViewById(R.id.pwd_pass_pwd);
        password_back = findViewById(R.id.password_back);
    }

    private void changePhone() {
        Log.e("修改pwd", "运行");
        String jsonStr = null;
        int id = sharedPreferences.getInt("id",0);
        String phone = sharedPreferences.getString("phone","");
        Log.e("修改", pwd);
        User user = new User(id,new_pwd,phone);
        jsonStr = new Gson().toJson(user);

        //2.创建RequestBody请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                jsonStr);
        //3.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "user/updatePwd")
                .post(body)//请求方式POST
                .build();
        //4.创建Call对象
        Call call = client.newCall(request);
        Log.e("change", "password");
        //5.发送请求(可以同步或者异步)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("查询", "-" + jsonStr);
                Toast.makeText(getBaseContext(),"密码修改成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PasswordActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
