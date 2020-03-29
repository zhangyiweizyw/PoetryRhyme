package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
    private View fragment;
    private LinearLayout layout_to_search;
    private ImageView home_avator;
    private DrawerLayout drawerLayout;
    private LinearLayout left_drawer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.tab_home_layout,container,false);

        //code begin
        findView();
        action();






        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }


    private void findView() {
        home_avator=fragment.findViewById(R.id.img__home_avator);
        layout_to_search=fragment.findViewById(R.id.linear_click_to_search);
        drawerLayout=fragment.findViewById(R.id.drawer_layout);
        left_drawer=fragment.findViewById(R.id.left_drawer);
    }
    private void action() {
        //点击后跳转到搜索界面
        layout_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Search.class));
            }
        });
        //点击头像后唤出抽屉
        home_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
