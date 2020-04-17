package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.CENTER;

public class AddPoemFragment extends Fragment{

    private View fragment;

    private List<User> userList=new ArrayList<>();
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
                    Community c=new Community("春眠","春眠不觉晓",100,date);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fragment=inflater.inflate(R.layout.addpoemview,container,false);
        //codebegin
        findViews();
        initData();



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
    //初始化布局
    private void initLayout(){
        LayoutInflater factory = LayoutInflater.from(getContext());
        View layout = factory.inflate(R.layout.community_list, null);
        TextView tv=(TextView) layout.findViewById(R.id.tv_title);
        Log.e("title",tv.getText().toString());
        tv.setGravity(Gravity.CENTER);

    }

    private void findViews(){
        listView =fragment.findViewById(R.id.lv_data);
        // initLayout();
        communityAdapter= new CommunityAdapter(fragment.getContext(),communityList,userList,R.layout.community_list,2);
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
                Thread.sleep(3000);
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
            Community c=new Community("春眠","春眠不觉晓",100,date);
            communityList.add(c);
            communityAdapter.notifyDataSetChanged();
            //结束加载更多的动画
            refreshLayout.finishLoadMore();
        }
    }



    //准备数据源
    private void initData(){
        User u = new User("李四","123446","default_headimg.png");
        User u1 = new User("李四","123446","default_headimg.png");
        User u2= new User("李四","123446","default_headimg.png");
        User u3 = new User("李四","123446","default_headimg.png");
        User u4= new User("李四","123446","default_headimg.png");
        userList.add(u);
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        long time=System.currentTimeMillis();
        Date date=new Date(time);

        Community c=new Community("静夜思","床前明月光，疑是地上霜。举头望明月，低头思故乡。",100,100,date);
        Community c1=new Community("惠崇春江晚景","竹外桃花三两枝，春江水暖鸭先知。蒌蒿满地芦芽短，正是河豚欲上时。",100,100,date);
        Community c2=new Community("绝句","迟日江山丽，春风花草香。泥融飞燕子，沙暖睡鸳鸯",100,100,date);
        Community c3=new Community("别董大","千里黄云白日曛，北风吹雁雪纷纷，。莫愁前路无知己，天下谁人不识君",100,100,date);
        Community c4=new Community("秋浦歌","白发三千丈，缘愁似个长。不知明镜里，何处得秋霜。",100,100,date);
        communityList.add(c);
        communityList.add(c1);
        communityList.add(c2);
        communityList.add(c3);
        communityList.add(c4);
    }
}