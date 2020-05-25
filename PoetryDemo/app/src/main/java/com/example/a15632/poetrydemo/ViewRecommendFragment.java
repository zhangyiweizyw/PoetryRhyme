package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.Poetry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.xbanner.XBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewRecommendFragment extends Fragment {
    private View fragment;
    private XBanner xBanner;
    private View view;
    private ListView listView;
    private ArrayList<Poetry> rec = new ArrayList<>();
    private MyAdapter<Poetry> myAdapter;
//  交互
    private static String ip = "http://192.168.0.103:8080/PoetryRhyme/";
    private OkHttpClient client;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.view_recommend_fragment, container, false);
        //code begin
        client = new OkHttpClient();
        initData();

        findView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),PoemDetail.class);
                intent.putExtra("poem",rec.get(position));
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

    private void initData() {
        /*rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落。"));
        rec.add(new Poetry("秋风词", "唐代/李白", "落叶聚还散，寒鸦栖复惊。"));
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？纵使青春留得住。虚语。"));
        rec.add(new Poetry("木兰花慢", "佚名", "问花花不语，为谁开，为谁落。"));
        rec.add(new Poetry("秋风词", "唐代/李白", "落叶聚还散，寒鸦栖复惊。"));
        rec.add(new Poetry("定风波", "欧阳修", "把酒花前欲问君，世间何计可留春？"));*/
        getAsync();
    }


    //GET
    private void getAsync() {
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(ip + "poetry/get")//设置网络请求的URL地址
                .get()
                .build();
        //3.创建Call对象
        Call call = client.newCall(request);
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
//                Log.e("异步GET请求结果",response.body().string());
                String jsonStr = response.body().string();
                Log.e("结果", "-" + jsonStr);

                /* User msg = new Gson().fromJson(jsonStr, User.class);*/
                List<Poetry> list = new Gson().fromJson(jsonStr,new TypeToken<List<Poetry>>(){}.getType());


                for(Poetry poetry:list){
                    Log.e("得到的诗句",poetry.toString());
                    rec.add(poetry);
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        action();
                    }
                });

//                response.close();


            }
        });
    }

    private void findView() {
        //轮播图备份
//        xBanner=fragment.findViewById(R.id.xbanner);
//        List<LocalImageEntity> lo=new ArrayList<>();
//        lo.add(new LocalImageEntity(R.drawable.banner_1));
//        lo.add(new LocalImageEntity(R.drawable.banner_2));
//        lo.add(new LocalImageEntity(R.drawable.banner_3));
//
//        xBanner.setBannerData(lo);
//        xBanner.loadImage(new XBanner.XBannerAdapter() {
//            @Override
//            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                ((ImageView)view).setImageResource(((LocalImageEntity)model).getXBannerUrl());
//            }
//        });
        listView = fragment.findViewById(R.id.listview_recommend);
    }

    private void action() {
        myAdapter = new MyAdapter<Poetry>(rec, R.layout.item_recommend) {
            @Override
            public void bindView(ViewHolder holder,
                                 Poetry obj) {
                holder.setText(R.id.tv_recommend_name, obj.getName());
                holder.setText(R.id.tv_recommend_author,obj.getAuthor());
                holder.setText(R.id.tv_recommend_content,obj.getContent());
            }
        };
        listView.setAdapter(myAdapter);
    }
}
