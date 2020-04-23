package com.example.a15632.poetrydemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jauker.widget.BadgeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class EditFragment extends Fragment {
    private View fragment;

    private boolean isNull=false;

    private SmartRefreshLayout refreshLayout;
    private LinearLayout layout;

    private ImageView iv_offical=null;
    private ImageView iv_system=null;
    private ImageView iv_comlike=null;
    private ImageView iv_attention=null;

    private int newscount=100;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.tab_edit_layout,container,false);

        //code begin
        findViews();
        //初始化布局
        initLayout();
        addBadgeView();

        MyListener myListener=new MyListener();
        iv_offical.setOnClickListener(myListener);
        iv_system.setOnClickListener(myListener);
        iv_comlike.setOnClickListener(myListener);
        iv_attention.setOnClickListener(myListener);










        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }

    public void findViews(){
        refreshLayout=fragment.findViewById(R.id.refreshLayout);
        layout=fragment.findViewById(R.id.layout_no);
        iv_offical=fragment.findViewById(R.id.iv_offical);
        iv_system=fragment.findViewById(R.id.iv_system);
        iv_comlike=fragment.findViewById(R.id.iv_comlike);
        iv_attention=fragment.findViewById(R.id.iv_attention);
    }


    public void initLayout(){
        //如果私信列表为空，则展示暂无私信，如果不为空，则展示私信列表
        if(!isNull){
            //私信列表为空
            layout.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
        else{
            //私信列表不为空
            layout.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_offical:
                    break;
                case R.id.iv_system:
                    break;
                case R.id.iv_comlike:
                    break;
                case R.id.iv_attention:
                    break;

            }
        }
    }

    //动态添加徽章控件
    public void addBadgeView(){
        BadgeView badgeView=new BadgeView(getContext());
        badgeView.setTargetView(iv_offical);
        badgeView.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);
        badgeView.setBadgeMargin(0,0,5,0);
        //真实消息数量从数据库获取
        if(newscount>99){
            badgeView.setText("99+");
        }
        else{
            badgeView.setBadgeCount(10);
        }
        BadgeView badgeView1=new BadgeView(getContext());
        badgeView1.setTargetView(iv_system);
        badgeView1.setBadgeGravity(Gravity.BOTTOM | Gravity.RIGHT);
        badgeView1.setBadgeMargin(0,0,5,0);
        //真实消息数量从数据库获取
        if(newscount>99){
            badgeView1.setText("99+");
        }
        else{
            badgeView1.setBadgeCount(10);
        }
    }
}
