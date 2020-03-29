package com.example.a15632.poetrydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Search extends AppCompatActivity {
    private EditText input;
    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_search);

        findView();
        action();
    }


    private void findView() {
        input=findViewById(R.id.et_home_input);
        cancel=findViewById(R.id.tv_back_to_home);

    }

    private void action() {
        //关于EditView的操作,相应虚拟键盘上的"搜索"按钮
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return false;
            }
        });
        //点击取消后返回首页
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
