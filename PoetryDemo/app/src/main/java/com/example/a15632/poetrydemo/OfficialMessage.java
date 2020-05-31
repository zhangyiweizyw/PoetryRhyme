package com.example.a15632.poetrydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.Msg;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

public class OfficialMessage extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private MyAdapter<Msg> myAdapter;
    private ArrayList<Msg>msgs=new ArrayList<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_msg_official);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorback));

        findViews();
        initViews();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void findViews(){
        toolbar=findViewById(R.id.title_bar);
        listView=findViewById(R.id.lv_data);
    }

    private void initViews(){

       getPrivateMsg();
        myAdapter=new MyAdapter<Msg>(msgs, R.layout.item_privatenews) {
            @Override
            public void bindView(ViewHolder holder, Msg obj) {
                holder.setText(R.id.username,obj.getUsername());
                holder.setText(R.id.latestnews,obj.getLatestnews());
                //holder.setText(R.id.date,obj.getDate().toString());
                holder.setImageResource(R.id.imageview,obj.getHeadpsth());
            }
        };
        listView.setAdapter(myAdapter);
    }
    public void getPrivateMsg(){
       /* long time=System.currentTimeMillis();
        Date date=new Date(time);*/
        Msg msg=new Msg("诗韵系统", R.drawable.systeminfor,"您好，您的系统以升至最新版本！",null);
        Msg msg1=new Msg("诗韵系统", R.drawable.systeminfor,"您好，欢迎您加入诗韵大家庭！",null);
        msgs.add(msg);
        msgs.add(msg1);

    }
}
