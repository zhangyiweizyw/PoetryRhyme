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
import android.widget.GridView;

import com.example.a15632.poetrydemo.Entity.Author;
import com.example.a15632.poetrydemo.Entity.CommunityTopic;
import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.Poetry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewSortFragment extends Fragment {
    private GridView gv1;
    private GridView gv2;
    private GridView gv3;
    private View fragment;
    private ArrayList<Author> author_people = new ArrayList<>();
    private ArrayList<String> author_time = new ArrayList<>();
    private ArrayList<String> author_theme = new ArrayList<>();
    private MyAdapter<Author> myAdapter1;
    private MyAdapter<String> myAdapter2;
    private MyAdapter<String> myAdapter3;

    //  交互
    private OkHttpClient client;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.view_sort_fragment, container, false);
        //code begin

        client = new OkHttpClient();
        initData();
        findView();

        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),SortDetail.class);
                intent.putExtra("sort",author_people.get(position).getName());
                startActivity(intent);
            }
        });

        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),SortDetail.class);
                intent.putExtra("sort",author_time.get(position));
                startActivity(intent);
            }
        });

        gv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),SortDetail.class);
                intent.putExtra("sort",author_theme.get(position));
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

        getAsync();
        getPoetry();
    }


    //GET
    private void getAsync() {
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "author/get")//设置网络请求的URL地址
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
                List<Author> list = new Gson().fromJson(jsonStr, new TypeToken<List<Author>>() {
                }.getType());


                for (Author author : list) {
                    Log.e("得到的诗句", author.toString());
                    author.setImg(R.drawable.avatar_unlogin);
                    author_people.add(author);
                }
                author_theme.add("古诗");
                author_theme.add("诗经");
                author_theme.add("诗词");

                author_time.add("唐朝");
                author_time.add("宋朝");
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
        gv1 = fragment.findViewById(R.id.gv_sort_by_person);
        gv2 = fragment.findViewById(R.id.gv_sort_by_time);
        gv3 = fragment.findViewById(R.id.gv_sort_by_theme);
    }

    private void action() {
        //1
        myAdapter1 = new MyAdapter<Author>(author_people, R.layout.item_sort) {
            @Override
            public void bindView(ViewHolder holder, Author obj) {
                holder.setImageResource(R.id.item_sorts_img, obj.getImg());
                holder.setText(R.id.item_sorts_title, obj.getName());
            }
        };
        gv1.setAdapter(myAdapter1);
        //2
        myAdapter2 = new MyAdapter<String>(author_time, R.layout.item_sort) {


            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setImageResource(R.id.item_sorts_img, R.drawable.avatar_unlogin);
                holder.setText(R.id.item_sorts_title, obj);
            }
        };
        gv2.setAdapter(myAdapter2);
        //3
        myAdapter3 = new MyAdapter<String>(author_theme, R.layout.item_sort) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setImageResource(R.id.item_sorts_img, R.drawable.avatar_unlogin);
                holder.setText(R.id.item_sorts_title, obj);
            }

        };
        gv3.setAdapter(myAdapter3);

    }


    //GET
    //测试
    private void getPoetry() {
        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "comm/get")//设置网络请求的URL地址
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
                String str = URLDecoder.decode(jsonStr, "utf-8");
                Log.e("结果", "-" + str);
                /* User msg = new Gson().fromJson(jsonStr, User.class);*/
                List<CommunityTopic> list = new Gson().fromJson(str, new TypeToken<List<CommunityTopic>>() {
                }.getType());


                for (CommunityTopic comm : list) {
                    Log.e("得到的社区", comm.toString());

                }


//                response.close();


            }
        });
    }
}
