package com.example.a15632.poetrydemo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import java.util.List;

public class CommunityFragment extends Fragment{
    //作为指示标签的按钮
    private ImageView cursor;

    //标志指示标签的横坐标
    float cursorX = 0;
    //所有按钮的宽度的数组
    private int[] widthArgs;
    //所有标题按钮的数组
    private Button[] btnArgs;
    //选项卡中的按钮
    private Button btn_addpoem=null;
    private Button btn_addtalk=null;


    private  ViewPager mViewPager;
    private View fragment;
    FragmentManager fragmentManager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;

    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.tab_community_layout,container,false);
        //code begin
        findViews();
        MyListener myListener=new MyListener();
        btn_addpoem.setOnClickListener(myListener);
        btn_addtalk.setOnClickListener(myListener);

        //页面切换
        fragmentManager =getChildFragmentManager();//定义fragment管理器
        initFragmetList();//初始化fragment的列表
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(fragmentManager,mFragmentList);//设置viewpager的适配器
        initViewPager();//初始化viewpager        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }

    public void findViews(){
        btn_addpoem=fragment.findViewById(R.id.btn_addpoem);
        btn_addtalk=fragment.findViewById(R.id.btn_addtalk);
        cursor = fragment.findViewById(R.id.cursor_btn);
    }


    public void initViewPager() {
        mViewPager = (ViewPager)fragment.findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setCurrentItem(0);



    }
    public void initFragmetList() {
        mFragmentList.clear();
        Fragment addPoemFragment = new AddPoemFragment();
        Fragment addTalkFragment=new AddTalkFragment();
        mFragmentList.add(addPoemFragment);
        mFragmentList.add(addTalkFragment);

        //初始化按钮数组
        btnArgs = new Button[]{btn_addpoem,btn_addtalk};
        //指示标签设置为红色
        cursor.setBackgroundColor(getResources().getColor(R.color.colorTheme));
        //首先默认选中第一项
        resetButtonColor();
        btn_addpoem.setTextColor(getResources().getColor(R.color.colorTheme));

    }
    private class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        @Override
        public void onPageSelected(int position) {
            Log.d("CHANGE","onPageSelected");
            if(widthArgs==null){
                widthArgs = new int[]{btn_addpoem.getWidth(),
                        btn_addtalk.getWidth()};
            }
            //每次滑动首先重置所有按钮的颜色
            resetButtonColor();
            //将滑动到的当前按钮颜色设置为红色
            btnArgs[position].setTextColor(getResources().getColor(R.color.colorTheme));
            cursorAnim(position);



        }
        @Override
        public void onPageScrollStateChanged(int state) { }
    }

    //重置所有按钮文字的颜色
    public void resetButtonColor(){
        btn_addpoem.setTextColor(getResources().getColor(R.color.colorText));
        btn_addtalk.setTextColor(getResources().getColor(R.color.colorText));
    }



    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_addpoem:
                    mViewPager.setCurrentItem(0);
                    btn_addpoem.setTextColor(getResources().getColor(R.color.colorTheme));
                    btn_addtalk.setTextColor(getResources().getColor(R.color.colorText));
                    break;
                case R.id.btn_addtalk:
                    mViewPager.setCurrentItem(1);
                    btn_addpoem.setTextColor(getResources().getColor(R.color.colorText));
                    btn_addtalk.setTextColor(getResources().getColor(R.color.colorTheme));
                    break;
            }
        }
    }

    //指示器的跳转，传入当前所处的页面的下标
    public void cursorAnim(int curItem) {
        //每次调用，就将指示器的横坐标设置为0，即开始的位置
        cursorX = 0;
        //再根据当前的curItem来设置指示器的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        //减去边距*2，以对齐标题栏文字
        lp.width = widthArgs[curItem] - btnArgs[0].getPaddingLeft() * 2;
        cursor.setLayoutParams(lp);
        //循环获取当前页之前的所有页面的宽度
        for (int i = 0; i < curItem; i++) {
            cursorX = cursorX + btnArgs[i].getWidth();
        }
        //再加上当前页面的左边距，即为指示器当前应处的位置
        cursor.setX(cursorX + btnArgs[curItem].getPaddingLeft());
    }


}
