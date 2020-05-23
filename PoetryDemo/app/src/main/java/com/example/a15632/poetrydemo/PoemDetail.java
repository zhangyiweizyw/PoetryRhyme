package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.a15632.poetrydemo.Entity.Poetry;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class PoemDetail extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Intent intent;
    private Toolbar toolbar;
    private Poetry poetry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_poem);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorback));

        findViews();
        initView();

    }
    private void findViews(){
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.mviewpager);
        intent=getIntent();
        toolbar=findViewById(R.id.toolbar);
        poetry=(Poetry)intent.getSerializableExtra("poem");


    }
    private void initView(){
        toolbar.setTitle(poetry.getName());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PoemContent());
        fragments.add(new PoemTranslate());
        fragments.add(new PoemWrite());
        List<String>titles=new ArrayList<>();
        titles.add("正文");
        titles.add("译文");
        titles.add("默写");
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        for(int i=0;i<titles.size();i++){
            adapter.AddFragment(fragments.get(i),titles.get(i));
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable d = null;
            switch (i) {
                case 0:
                    d = getResources().getDrawable(R.drawable.book);
                    break;
                case 1:
                    d = getResources().getDrawable(R.drawable.translate);
                    break;
                case 2:
                    d = getResources().getDrawable(R.drawable.write);
                    break;
            }
            tab.setIcon(d);
        }
    }


}



