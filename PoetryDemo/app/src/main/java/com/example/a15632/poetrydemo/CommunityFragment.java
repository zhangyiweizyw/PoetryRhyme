package com.example.a15632.poetrydemo;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;

public class CommunityFragment extends Fragment{
    private  ViewPager mViewPager;
    private View fragment;
    private TabLayout mTabLayout;

    private TitleLayout titleLayout;
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
        initViews();
        MyListener myListener=new MyListener();
        //发布按钮
        titleLayout.setRightIconOnClickListener(myListener);
        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }

    public void findViews(){
        mViewPager=(ViewPager) fragment.findViewById(R.id.viewpager);
        mTabLayout=(TabLayout) fragment.findViewById(R.id.tabLayout);
        titleLayout=fragment.findViewById(R.id.title_bar);
    }
    private void initViews(){
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.AddFragment(new AddPoemFragment(),"原创诗词");
        viewPagerAdapter.AddFragment(new AddTalkFragment(),"社区话题");
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.addcommunity:
                    // 弹出PopupMenu
                    showPopupMenu(v);
            }
        }
    }
    private void showPopupMenu(View v) {
        PopupMenu popupMenu
                = new PopupMenu(fragment.getContext(), v);
        popupMenu.getMenuInflater()
                .inflate(R.menu.toolbar, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(fragment.getContext(),
                                item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()){
                            case R.id.item_addpoem:
                                showPopupWindow(fragment);
                                break;
                            case R.id.item_addtalk:
                                showPopupWindow(fragment);
                                break;
                        }
                        return false;
                    }
                }
        );
        popupMenu.show();

    }
    //显示弹窗
    private void showPopupWindow(View v){
        View popupWindowView=getLayoutInflater().inflate(R.layout.addcommunity,null);
        final PopupWindow popupWindow=new PopupWindow(popupWindowView, ActionBar.LayoutParams.MATCH_PARENT ,ActionBar.LayoutParams.MATCH_PARENT, true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
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
        View parentView = LayoutInflater.from(getContext()).inflate(R.layout.comment_popupwindow, null);
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