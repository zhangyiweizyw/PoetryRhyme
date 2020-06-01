package com.example.a15632.poetrydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.SystemMsg;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

public class SystemMessage extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private MyAdapter<SystemMsg> myAdapter;
    private ArrayList<SystemMsg>systemMsgs=new ArrayList<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_msg_system);
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
        SystemMsg systemMsg=new SystemMsg("重磅消息", R.drawable.shici3,"诗韵官方将和《中国诗词大会》节目组联合举办第一届线上诗词大会！届时，不仅有来自天南地北的小伙伴们共同切磋诗词，还可以进行诗词交流探讨，本活动旨在传扬中国传统优秀诗词文化，诚邀小伙伴们积极参加！");
        SystemMsg systemMsg1=new SystemMsg("我与诗词征文比赛", R.drawable.img4,"诗韵官方将于六月十号开展“我与诗词”征文比赛。小伙伴们可以尽情书写你与诗词之间的故事。文体不限，1500字以上。希望大家踊跃参加，官方为大家准备了丰厚的礼品哦！");
        systemMsgs.add(systemMsg);
        systemMsgs.add(systemMsg1);
        myAdapter=new MyAdapter<SystemMsg>(systemMsgs, R.layout.item_system) {
            @Override
            public void bindView(ViewHolder holder, SystemMsg obj) {
                holder.setText(R.id.tv_msgtitle,obj.getTitle());
                holder.setImageResource(R.id.iv_msgimg,obj.getImg());
                holder.setText(R.id.tv_msgcontent,obj.getContent());

            }
        };
        listView.setAdapter(myAdapter);
    }
}
