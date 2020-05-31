package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.Community;
import com.example.a15632.poetrydemo.Entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddAttention extends Fragment {
    private View fragment;

    private XBanner banner;

    private ListView listView;
    private MyAdapter<Community> myAdapter;
    private ArrayList<Community>communities=new ArrayList<>();
    private OkHttpClient okHttpClient=new OkHttpClient();
    private String ip="http://192.168.0.57:8080/MyPoetryRhyme/";
    private SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull final LayoutInflater inflater,
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
                intent.putExtra("community",communities.get(position));
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
        sharedPreferences = getContext().getSharedPreferences("loginInfo",
                MODE_PRIVATE);
        initData();
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

    //准备数据源
    private void initData(){
        //获取当前userid，以2为例
        int myuserid=sharedPreferences.getInt("id",0);
        //访问数据库
        //2.创建Request对象
        Gson gson=new Gson();
        MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
        String jsonStr = gson.toJson(myuserid);//json数据.
        RequestBody body = RequestBody.create(jsonType, jsonStr);
        Request request = new Request.Builder()
                .url(ip+"foucscomm/get")//设置网络请求的URL地址
                .post(body)
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
                Log.e("content",str);
                communities=(ArrayList<Community>) parseJsonStr(str);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter=new MyAdapter<Community>(communities,R.layout.item_attention) {
                            @Override
                            public void bindView(ViewHolder holder, Community obj) {
                                // holder.setImageResource(R.id.imageview,obj.getUser().getHeadimg());
                                holder.setText(R.id.username,obj.getUser().getName());
                                //holder.setText(R.id.tv_date,obj.getIssuedate().toString());
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



