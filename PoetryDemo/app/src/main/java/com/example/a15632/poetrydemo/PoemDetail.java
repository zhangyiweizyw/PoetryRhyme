package com.example.a15632.poetrydemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class PoemDetail extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_poem);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorback));

        findViews();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PoemContent());
        fragments.add(new PoemContent());
        fragments.add(new PoemContent());

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
                    d = getResources().getDrawable(R.drawable.setup2);
                    break;
                case 1:
                    d = getResources().getDrawable(R.drawable.set);
                    break;
                case 2:
                    d = getResources().getDrawable(R.drawable.collect1);
                    break;
                case 3:
                    d = getResources().getDrawable(R.drawable.search);
                    break;
            }
            tab.setIcon(d);
        }
    }
    private void findViews(){
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.mviewpager);

    }


}



