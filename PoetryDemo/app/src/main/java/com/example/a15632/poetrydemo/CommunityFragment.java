package com.example.a15632.poetrydemo;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommunityFragment extends Fragment {
    private int imgflag=0;
    private View fragment;
    private List<User>userList=new ArrayList<>();
    private List<Community>communityList=new ArrayList<>();
    private ListView listView;
    private CommunityAdapter communityAdapter;
    private SmartRefreshLayout refreshLayout;
    private static final int REFRESH_FINISH = 1;
    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_FINISH:
                    User u = new User("zhsan","123446","default_headimg.png");
                    // userList.add(u);
                    long time=System.currentTimeMillis();
                    Date date=new Date(time);
                    Community c=new Community("春眠","春眠不觉晓",100,100,date);
                    //修改数据源
                    userList.add(0,u);
                    communityList.add(0,c);
                    //更新Adapter
                    communityAdapter.notifyDataSetChanged();
                    //结束刷新动画
                    refreshLayout.finishRefresh();
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragment=inflater.inflate(R.layout.tab_community_layout,container,false);
        /*ActionBar actionBar=getActivity().getActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }*/
        //code begin

        initData();
        findViews();
        setListeners();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),CommunityDetail.class);
                startActivity(intent);
            }
        });







        //code end
        ViewGroup p=(ViewGroup)fragment.getParent();
        if(p!=null){
            p.removeView(fragment);
        }

        return fragment;
    }
    private void findViews(){
        listView =fragment.findViewById(R.id.lv_data);
        communityAdapter= new CommunityAdapter(fragment.getContext(),communityList,userList,R.layout.community_list);
        listView.setAdapter(communityAdapter);

        //获取智能刷新布局
        refreshLayout = fragment.findViewById(R.id.refreshLayout);
    }

    private void setListeners(){
        //监听下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //不能执行网络操作，需要使用多线程
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //向主线程发送消息，更新视图
                        Message msg = new Message();
                        msg.what = REFRESH_FINISH;
                        mainHandler.sendMessage(msg);
                    }
                }.start();

            }
        });

        //监听上拉加载更多
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                CommunityListTask task = new CommunityListTask();
                task.execute();
            }
        });
    }

    private class CommunityListTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //更新视图
            User u = new User("zhsan","123446","default_head.png");
            userList.add(u);
            long time=System.currentTimeMillis();
            Date date=new Date(time);
            Community c=new Community("春眠","春眠不觉晓",100,100,date);
            communityList.add(c);
            communityAdapter.notifyDataSetChanged();
            //结束加载更多的动画
            refreshLayout.finishLoadMore();
        }
    }

    //准备数据源
    private void initData(){
        User u = new User("李四","123446","default_headimg.png");
        userList.add(u);
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        Community c=new Community("静夜思","床前明月光",100,100,date);
        communityList.add(c);
    }
    //喜欢取消喜欢
/*    public void isLike(View v){
        ImageView headimg=v.findViewById(R.id.like);
        headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgflag==0){
                    Log.e("flag",imgflag+"");
                    ImageView headimg1=v.findViewById(R.id.like);
                    Drawable drawable=getResources().getDrawable(R.drawable.heart_red);
                    headimg1.setImageDrawable(drawable);
                    imgflag=1;
                    //喜欢的数量加1，更新界面，更新数据库
                }
                else{
                    ImageView headimg1=v.findViewById(R.id.like);
                    Drawable drawable=getResources().getDrawable(R.drawable.heart_gray);
                    headimg1.setImageDrawable(drawable);
                    imgflag=0;
                    //喜欢的数量减1，更新界面，更新数据库
                }
            }
        });
    }*/
}
