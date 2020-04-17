package com.example.a15632.poetrydemo;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;

public class CommunityFragment extends Fragment{
    //选项卡中的按钮
    private Button btn_addpoem=null;
    private Button btn_addtalk=null;

    private View cursor;
    private int offset = 0;
    private  int screenWidth = 0;
    private int screenl_3;
    private LinearLayout.LayoutParams lp;


    private  ViewPager mViewPager;
    private View fragment;
    FragmentManager fragmentManager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;

    private TitleLayout titleLayout;
    private Button btn_addCommuniy=null;
    private ImageView iv_delete=null;

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
        //发布按钮
        titleLayout.setRightIconOnClickListener(myListener);

        //页面切换
        fragmentManager =getChildFragmentManager();//定义fragment管理器
        initFragmetList();//初始化fragment的列表
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(fragmentManager,mFragmentList);//设置viewpager的适配器
        initViewPager();//初始化viewpager        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();

        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenl_3 = screenWidth/2;  //裁剪2分之1
        lp = (LinearLayout.LayoutParams)cursor.getLayoutParams();





        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }

    public void findViews(){
        btn_addpoem=fragment.findViewById(R.id.btn_addpoem);
        btn_addtalk=fragment.findViewById(R.id.btn_addtalk);
        cursor = fragment.findViewById(R.id.cursor);
        mViewPager = (ViewPager)fragment.findViewById(R.id.viewpager);
        titleLayout=fragment.findViewById(R.id.title_bar);
    }


    public void initViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);



    }
    public void initFragmetList() {
        mFragmentList.clear();
        Fragment addPoemFragment = new AddPoemFragment();
        Fragment addTalkFragment=new AddTalkFragment();
        mFragmentList.add(addPoemFragment);
        mFragmentList.add(addTalkFragment);

    }
    private class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {



        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            offset = (screenl_3-cursor.getLayoutParams().width)/2;
            Log.d("TAG", "111----"+position + "--" + positionOffset + "--"
                    + positionOffsetPixels);
            final float scale = getResources().getDisplayMetrics().density;
            if (position == 0){
                lp.leftMargin = (int)(positionOffsetPixels/2)+offset;
            }else if(position ==1){
                lp.leftMargin = (int)(positionOffsetPixels/2)+screenl_3+offset;
            }
            cursor.setLayoutParams(lp);
            upTextcolor(position);

        }
        @Override
        public void onPageSelected(int position) {
            Log.d("CHANGE","onPageSelected");
        }
        @Override
        public void onPageScrollStateChanged(int state) { }
    }

    private void upTextcolor(int position){
        if (position==0){
            btn_addpoem.setTextColor(getResources().getColor(R.color.colorTheme));
            btn_addtalk.setTextColor(getResources().getColor(R.color.colorText));
        }else if(position==1){
            btn_addpoem.setTextColor(getResources().getColor(R.color.colorText));
            btn_addtalk.setTextColor(getResources().getColor(R.color.colorTheme));
        }else if(position==2){
            btn_addpoem.setTextColor(getResources().getColor(R.color.colorTheme));
            btn_addtalk.setTextColor(getResources().getColor(R.color.colorText));
        }
    }
    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_addpoem:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.btn_addtalk:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.addcommunity:
                    // 弹出PopupMenu
                    showPopupMenu(v);
            }
        }
    }

    private void showPopupMenu(View v) {
        // 1. 创建PopupMenu
        PopupMenu popupMenu
                = new PopupMenu(fragment.getContext(), v);
        // 2. 通过XML资源文件对PopupMenu进行填充
        popupMenu.getMenuInflater()
                .inflate(R.menu.toolbar, popupMenu.getMenu());
        // 3. 绑定监听器
        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(fragment.getContext(),
                                item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()){
                            case R.id.item_addpoem:
                               /* Intent intent=new Intent(getActivity(),AddCommunity.class);
                                startActivity(intent);*/
                                showPopupWindow(fragment);
                                break;
                            case R.id.item_addtalk:
                               /* Intent intent1=new Intent(getActivity(),AddCommunity.class);
                                startActivity(intent1);*/
                                showPopupWindow(fragment);
                                break;
                        }

                        return false;
                    }
                }
        );
        // 4. 显示PopupMenu
        popupMenu.show();

    }

    //显示弹窗
    private void showPopupWindow(View v){
        View popupWindowView=getLayoutInflater().inflate(R.layout.addcommunity,null);
        final PopupWindow popupWindow=new PopupWindow(popupWindowView, ActionBar.LayoutParams.MATCH_PARENT ,ActionBar.LayoutParams.MATCH_PARENT, true);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //弹出动画
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        //引入依附的布局
        View parentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_popupwindow, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        //点击叉号按钮
        iv_delete=popupWindowView.findViewById( R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"返回",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });


    }










}
