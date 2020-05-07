package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.Community;
import com.example.a15632.poetrydemo.Entity.User;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AddAttention extends Fragment {
    private View fragment;

    private XBanner banner;

    private ListView listView;
    private MyAdapter<Community> myAdapter;
    private ArrayList<Community>communities=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.activity_attention, container, false);
        //code begin
        findView();
        initView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),CommunityDetail.class);
                intent.putExtra("isAttention",true);
                startActivity(intent);
            }
        });


        //code end
        ViewGroup p = (ViewGroup) fragment.getParent();
        if (p != null) {
            p.removeView(fragment);
        }

        return fragment;
    }

    private void findView(){
        banner=fragment.findViewById(R.id.xbanner);
        listView=fragment.findViewById(R.id.lv_data);
        initData();
        myAdapter=new MyAdapter<Community>(communities, R.layout.item_attention) {
            @Override
            public void bindView(ViewHolder holder, Community obj) {
                holder.setImageResource(R.id.imageview,obj.getUser().getHeadimg());
                holder.setText(R.id.username,obj.getUser().getUsername());
                holder.setText(R.id.tv_date,obj.getTime().toString());
                if(obj.getType()==1){
                    holder.setText(R.id.tv_type,"原创诗词");
                }else{
                    holder.setText(R.id.tv_type,"社区话题");
                }
                holder.setText(R.id.tv_title,obj.getTitle());
                holder.setText(R.id.tv_content,obj.getContent());

            }
        };
        listView.setAdapter(myAdapter);
    }
    private void initView(){
        List<LocalImageInfo> localImageInfoList=new ArrayList<>();
        localImageInfoList.add(new LocalImageInfo(R.drawable.banner_4));
        localImageInfoList.add(new LocalImageInfo(R.drawable.banner_2));
        localImageInfoList.add(new LocalImageInfo(R.drawable.banner_3));
        banner.setBannerData(localImageInfoList);
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ((ImageView) view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        });
        banner.setPageChangeDuration(3000);
        banner.setPageTransformer(Transformer.Default);//横向移动
    }

    private void initData(){
        User u=new User("张三","123456", R.drawable.default_headimg);
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        Community c2=new Community("夏至","李核垂腰祝饐，粽丝系臂扶羸。节物竞随乡俗，老翁闲伴儿嬉。",date,1,u);
        Community c=new Community("为什么李白被称为诗仙","李白字太白号青莲居士，世人尊称诗仙…",date,2,u);
        Community c1=new Community("绝句","迟日江山丽，春风花草香。泥融飞燕子，沙暖睡鸳鸯。",date,1,u);
        communities.add(c2);
        communities.add(c);
        communities.add(c1);
        communities.add(c1);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }





}



