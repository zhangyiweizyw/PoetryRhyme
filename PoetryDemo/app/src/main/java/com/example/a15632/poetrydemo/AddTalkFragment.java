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

import com.example.a15632.poetrydemo.Entity.Community;
import com.example.a15632.poetrydemo.Entity.User;
//import com.example.a15632.poetrydemo.util.VisitCommunityUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.Gravity.CENTER;

public class AddTalkFragment extends Fragment{

    private View fragment;
    private ArrayList<Community>communityList=new ArrayList<>();
    private MyAdapter<Community>myAdapter;
    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    //private VisitCommunityUtil visitCommunityUtil=new VisitCommunityUtil();
    private static final int REFRESH_FINISH = 1;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private String ip="http://192.168.1.101:8080/MyPoetryRhyme/";
    /*private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_FINISH:
                    //结束刷新动画
                    refreshLayout.finishRefresh();
                    break;
            }
        }
    };*/
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
                int count=communityList.get(position).getPageview();
                communityList.get(position).setPageview(count+1);
                intent.putExtra("community",communityList.get(position));
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
        initData();
       /* myAdapter=new MyAdapter<Community>(communityList,R.layout.community_list) {
            @Override
            public void bindView(ViewHolder holder, Community obj) {
                holder.setText(R.id.tv_title,obj.getTitle());
                //holder.setImageResource(R.id.iv_userhead,obj.getUser().getHeadimg());
                holder.setText(R.id.username,obj.getUser().getUsername());
                holder.setText(R.id.tv_content,obj.getContent());
                holder.setText(R.id.c_type,"社区话题");
                //holder.setText(R.id.tv_time,obj.getTime().toString());
                holder.setText(R.id.seecount,obj.getPageview()+"");
            }
        };
        listView.setAdapter(myAdapter);*/
        //获取智能刷新布局
        refreshLayout = fragment.findViewById(R.id.refreshLayout);
    }
    private void setListeners(){
        //监听下拉刷新
       /* refreshLayout.setOnRefreshListener(new OnRefreshListener() {
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
        });*/

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
            //结束加载更多的动画
            refreshLayout.finishLoadMore();
        }
    }
    //准备数据源
    //准备数据源
    private void initData(){
        //访问数据库
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(ip+"comm/get")//设置网络请求的URL地址
                .get()
                .build();
        //3.创建Call对象
        Call call = okHttpClient.newCall(request);
        //4.发送请求
        //异步请求，不需要创建线程
        call.enqueue(new Callback() {
            @Override
            //请求失败时回调
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();//打印异常信息
            }

            @Override
            //请求成功之后回调
            public void onResponse(Call call, Response response) throws IOException {
                //不能直接更新界面
                String jsonStr = response.body().string();
                String str = URLDecoder.decode(jsonStr, "utf-8");
                communityList=(ArrayList<Community>) parseJsonStr(str);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter=new MyAdapter<Community>(communityList,R.layout.community_list) {
                            @Override
                            public void bindView(ViewHolder holder, Community obj) {
                                holder.setText(R.id.tv_title,obj.getTitle());
                                //holder.setImageResource(R.id.iv_userhead,obj.getUser().getHeadimg());
                                holder.setText(R.id.username,obj.getUser().getName());
                                holder.setText(R.id.tv_content,obj.getContent());
                                holder.setText(R.id.c_type,"社区话题");
                                // holder.setText(R.id.tv_time,obj.getIssuedate().toString());
                                holder.setText(R.id.seecount,obj.getPageview()+"");
                            }
                        };
                        listView.setAdapter(myAdapter);
                    }
                });


            }
        });

    }
    public List<Community> parseJsonStr(String jsondata){
        Gson gson=new Gson();
        List<Community>communities=gson.fromJson(jsondata,new TypeToken<List<Community>>(){}.getType());
        for(int i=0;i<communities.size();i++){
            Log.e("community",communities.get(i).toString());
        }
        return communities;
    }
}