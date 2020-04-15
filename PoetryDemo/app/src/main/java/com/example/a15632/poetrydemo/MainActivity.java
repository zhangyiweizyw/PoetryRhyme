package com.example.a15632.poetrydemo;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FragmentTabHost tabHost=null;
    private String[] tabStrArr={"首页","社区","消息","游戏"};
    private Class[] fragmentArr={HomeFragment.class,CommunityFragment.class,
    EditFragment.class,GameFragment.class};
    private int[] imageNormalArr={R.drawable.homee,
            R.drawable.compass,
            R.drawable.news_normal,
            R.drawable.gamepad
    };
    private int[] imageClickArr={R.drawable.homee_click,
            R.drawable.compass_click,
            R.drawable.news_click,
            R.drawable.gamepad_click
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_main);

        initTabHost();

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for(int i=0;i<fragmentArr.length;++i){
                    if(tabStrArr[i].equals(tabId)){
                        ((TextView)tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.tv_text)).setTextColor(Color.rgb(216,30,6));
                        ((ImageView)tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.iv_image)).setImageResource(imageClickArr[i]);
                    }
                    else{
                        ((TextView)tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.tv_text)).setTextColor(Color.rgb(205, 205, 205));
                        ((ImageView)tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.iv_image)).setImageResource(imageNormalArr[i]);
                    }
                }
            }
        });
    }

    private void initTabHost() {
        tabHost=findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        for(int i=0;i<fragmentArr.length;++i){
            TabHost.TabSpec tabSpec
                    =tabHost.newTabSpec(tabStrArr[i])
                    .setIndicator(getTabSpecView(i));
            tabHost.addTab(tabSpec,fragmentArr[i],null);

        }
    }
    // 创建TabSpec显示的View
    private View getTabSpecView(int i) {
        View view = getLayoutInflater()
                .inflate(R.layout.tabhost_spec, null);

        ImageView imageView1 = view.findViewById(R.id.iv_image);
        imageView1.setImageResource(imageNormalArr[i]);

        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(tabStrArr[i]);

        if(0==i){
            textView.setTextColor(Color.rgb(216,30,6));
            imageView1.setImageResource(imageClickArr[i]);
        }else{
            textView.setTextColor(Color.rgb(205, 205, 205));
        }


        return view;
    }
}
