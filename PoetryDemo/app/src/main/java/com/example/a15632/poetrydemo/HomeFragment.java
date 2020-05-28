package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a15632.poetrydemo.drawer.AboutActivity;
import com.example.a15632.poetrydemo.drawer.CollectActivity;
import com.example.a15632.poetrydemo.drawer.SettingActivity;
import com.example.a15632.poetrydemo.drawer.WorksActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private View fragment;
    private LinearLayout layout_to_search;
    private ImageView home_avator;
    private DrawerLayout drawerLayout;
    private TextView tv_name;

    private LinearLayout left_drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CircleImageView circleImageView;
    private ImageView daka;//打卡
    private LinearLayout collectLayout;//收藏
    private LinearLayout worksLayout;//作品
    private LinearLayout settingLayout;//设置
    private LinearLayout aboutLayout;//关于
    private SharedPreferences sharedPreferences;
    private String name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.tab_home_layout,container,false);

        //code begin
        findView();
        action();



        sharedPreferences = getContext().getSharedPreferences("loginInfo",
                MODE_PRIVATE);
        name = sharedPreferences.getString("name","");

        if(!name.equals(null)){
            tv_name.setText(name);
        }
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
        tabLayout=fragment.findViewById(R.id.tabLayout_id);
        viewPager=fragment.findViewById(R.id.viewpager_id);
        circleImageView=fragment.findViewById(R.id.img_avator);
        daka=fragment.findViewById(R.id.img_into_daka);

        tv_name = fragment.findViewById(R.id.tv_name);
        //drawer
        collectLayout=fragment.findViewById(R.id.linear_collect);
        worksLayout=fragment.findViewById(R.id.linear_works);
        settingLayout=fragment.findViewById(R.id.linear_setting);
        aboutLayout=fragment.findViewById(R.id.linear_about);

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
        //TabLayout的设置
        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddFragment(new ViewRecommendFragment(),"推荐");
        adapter.AddFragment(new ViewSortFragment(),"分类");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //点击后跳转到登录界面
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        //点击跳转到打卡页面
        daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),DakaActivity.class));
            }
        });
        //drawer里的点击跳转
        collectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CollectActivity.class));
            }
        });
        worksLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),WorksActivity.class));
            }
        });
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SettingActivity.class));
            }
        });
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AboutActivity.class));
            }
        });
    }
}
