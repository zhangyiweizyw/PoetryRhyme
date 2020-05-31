package com.example.a15632.poetrydemo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Msg;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.sql.Date;
import java.util.ArrayList;

import q.rorbin.badgeview.QBadgeView;

public class EditFragment extends Fragment {
    private View fragment;

    private boolean isNull=true;

    private SmartRefreshLayout refreshLayout;
    private ListView listView;
    private MyAdapter<Msg> myAdapter;
    private ArrayList<Msg>msgs=new ArrayList<>();
    private LinearLayout layout;

    private ImageView iv_offical=null;
    private ImageView iv_system=null;
    private ImageView iv_comlike=null;
    private ImageView iv_attention=null;


    private static final int REFRESH_FINISH = 1;
    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_FINISH:
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
        fragment=inflater.inflate(R.layout.tab_edit_layout,container,false);

        //code begin
        getPrivateMsg();
        findViews();
        //初始化布局
        initLayout();


        setListeners();

        final MyListener myListener=new MyListener();
        iv_offical.setOnClickListener(myListener);
        iv_system.setOnClickListener(myListener);
        iv_comlike.setOnClickListener(myListener);
        //iv_attention.setOnClickListener(myListener);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                showPopupWindow(view,position);
                return false;
            }
        });
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
        //iv_attention=fragment.findViewById(R.id.iv_attention);
        listView=fragment.findViewById(R.id.lv_data);
        myAdapter=new MyAdapter<Msg>(msgs,R.layout.item_privatenews) {
            @Override
            public void bindView(ViewHolder holder, Msg obj) {
                holder.setText(R.id.username,obj.getUsername());
                holder.setText(R.id.latestnews,obj.getLatestnews());
                holder.setText(R.id.date,obj.getDate().toString());
                holder.setImageResource(R.id.imageview,obj.getHeadpsth());

            }
        };
        listView.setAdapter(myAdapter);
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
                MsgListTask task=new MsgListTask();
                task.execute();
            }
        });
    }

    private class MsgListTask extends AsyncTask {
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
            //结束加载更多的动画
            refreshLayout.finishLoadMore();
        }
    }


    public void initLayout(){
        //如果私信列表为空，则展示暂无私信，如果不为空，则展示私信列表
        if(isNull){
            //私信列表为空
            layout.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        }
        else{
            //私信列表不为空
            layout.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        //获得消息个数
        addBadgeView(iv_offical,2);
        addBadgeView(iv_system,2);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_offical:
                    Intent intent1=new Intent(getActivity(),SystemMessage.class);
                    startActivity(intent1);
                    break;
                case R.id.iv_system:
                    Intent intent2=new Intent(getActivity(),OfficialMessage.class);
                    startActivity(intent2);
                    break;
                case R.id.iv_comlike:
                    Intent intent=new Intent(getActivity(),ComLikeActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }

    //动态添加徽章控件
    public void addBadgeView(View v,int count){
        QBadgeView qBadgeView=new QBadgeView(getContext());
        qBadgeView.bindTarget(v);
        qBadgeView.setBadgeGravity(Gravity.END | Gravity.BOTTOM);
        //真实消息数量从数据库获取
        if(count>99){
            qBadgeView.setBadgeText("99+");
        }
        else{
            qBadgeView.setBadgeNumber(count);
        }
    }
    public void getPrivateMsg(){
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        Msg msg=new Msg("李四",R.drawable.default_head,"你好，我想问你个问题",date);
        Msg msg1=new Msg("张三",R.drawable.default_headimg,"你好，在吗？",date);
        msgs.add(msg);
        msgs.add(msg1);
        isNull=false;

    }

    //显示弹窗
    private void showPopupWindow(View v,int position){
        final int myposition=position;
        View popupWindowView=getLayoutInflater().inflate(R.layout.news_popup,null);
        final PopupWindow popupWindow=new PopupWindow(popupWindowView, ActionBar.LayoutParams.MATCH_PARENT ,ActionBar.LayoutParams.WRAP_CONTENT, true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.9f;
        getActivity().getWindow().setAttributes(lp);
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
        View parentView = LayoutInflater.from(getContext()).inflate(R.layout.news_popup, null);
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        //点击删除按钮
        LinearLayout layout_delete=popupWindowView.findViewById(R.id.lay_delete);
        layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msgs.remove(myposition)!=null){
                    System.out.println("success");
                }else {
                    System.out.println("failed");
                }
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                if(msgs.size()==0){
                    layout.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
                popupWindow.dismiss();
            }
        });


    }
}
