package com.example.a15632.poetrydemo.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.MainActivity;
import com.example.a15632.poetrydemo.R;

public class SettingActivity extends AppCompatActivity {
    //弹窗
    private PopupWindow popupWindow = null;
    private View popupView = null;
    //头像
    private ImageView head;
    //昵称
    private TextView name;
    //手机
    private Button change_phone;
    //密码
    private Button change_pwd;
    //退出
    private Button exit;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_setting);

        find();
        listener();

    }

    private void find() {
        head = findViewById(R.id.set_head);
        name = findViewById(R.id.set_name);
        change_pwd = findViewById(R.id.set_change_pwd);
        change_phone = findViewById(R.id.set_change_phone);
        exit = findViewById(R.id.set_exit);
    }


    private void listener() {
        //注销
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示PopupWindow
                if(popupWindow==null || !popupWindow.isShowing())
                    showExit();
            }
        });
        //手机
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });


        //密码
        change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });


    }

    private void showExit() {
        // 创建popupWindow对象
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 通过布局填充器创建View
        popupView = getLayoutInflater()
                .inflate(R.layout.popup_exit, null);
        // 设置PopupWindow显示的内容视图
        popupWindow.setContentView(popupView);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(false);
        // 设置PopupWindow是否相应点击事件
        popupWindow.setTouchable(true);

        // 获取按钮并添加监听器

        Button btnTrue = popupView.findViewById(R.id.exit_true);
        Button btnCancel = popupView.findViewById(R.id.exit_cancel);

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                sharedPreferences = getSharedPreferences("loginInfo",
                        MODE_PRIVATE);//只在本程序内部访问
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),"您已成功退出登陆！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // 在指定控件下方显示PopupWindow
        popupWindow.showAsDropDown(head);

    }
}
