package com.example.a15632.poetrydemo;


import android.app.ActionBar;
import android.content.Intent;
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

public class CommunityFragment extends Fragment {
    private ViewPager mViewPager;
    private View fragment;
    private TabLayout mTabLayout;

    private TitleLayout titleLayout;
    private ImageView iv_delete = null;

    List<Fragment> mFragmentList = new ArrayList<Fragment>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.tab_community_layout, container, false);
        //code begin
        findViews();
        initViews();
        MyListener myListener = new MyListener();
        //发布按钮
        titleLayout.setRightIconOnClickListener(myListener);
        //code end
        ViewGroup p = (ViewGroup) fragment.getParent();
        if (p != null) {
            p.removeView(fragment);
        }

        return fragment;
    }

    public void findViews() {
        mViewPager = (ViewPager) fragment.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) fragment.findViewById(R.id.tabLayout);
        titleLayout = fragment.findViewById(R.id.title_bar);
    }

    private void initViews() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.AddFragment(new AddAttention(), "我的关注");
        viewPagerAdapter.AddFragment(new AddPoemFragment(), "原创诗词");
        viewPagerAdapter.AddFragment(new AddTalkFragment(), "社区话题");
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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
                        switch (item.getItemId()) {
                            case R.id.item_addpoem:
                                Intent intent = new Intent(getActivity(), AddCommunity.class);
                                intent.putExtra("type", 1);
                                startActivity(intent);
                                break;
                            case R.id.item_addtalk:
                                Intent intent1 = new Intent(getActivity(), AddCommunity.class);
                                intent1.putExtra("type", 2);
                                startActivity(intent1);
                                break;
                        }
                        return false;
                    }
                }
        );
        popupMenu.show();

    }

}