package com.example.a15632.poetrydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a15632.poetrydemo.Entity.Constant;
import com.example.a15632.poetrydemo.Entity.Poetry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SortDetail extends AppCompatActivity {

    private ListView listView;
    private String str;
    private ArrayList<Poetry> resource = new ArrayList<>();
    private MyAdapter<Poetry> myAdapter;

    //  交互
    private OkHttpClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sort);
        client = new OkHttpClient();
        Intent intent = getIntent();
        str = intent.getStringExtra("sort");
        findView();
        switch (str) {
            case "诗词":
                list();
                break;
            case "古诗":

                break;

            case "诗经":
                break;
            case "唐朝":
                search("T");
                break;
            case "宋朝":
                search("S");
                break;
            default:
                search(str);
                break;

        }



        setListener();

    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SortDetail.this, PoemDetail.class);
                intent.putExtra("poem", resource.get(position));
                startActivity(intent);
            }
        });

    }

    private void findView() {
        listView = findViewById(R.id.list_sort);

    }

    //search
    private void search(String str) {

        Log.e("开启", "运行");
        String jsonStr = null;
        jsonStr = new Gson().toJson(str);

        //2.创建RequestBody请求体对象
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),
                jsonStr);
        //3.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "poetry/search")
                .post(body)//请求方式POST
                .build();
        //4.创建Call对象
        Call call = client.newCall(request);
        Log.e("开启", "运行前");
        //5.发送请求(可以同步或者异步)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                Log.e("post请求结果", response.message().toString());

                Log.e("查询的诗句", "-" + jsonStr);

                List<Poetry> list = new Gson().fromJson(jsonStr, new TypeToken<List<Poetry>>() {
                }.getType());
                resource.clear();
                for (Poetry poetry : list) {
                    Log.e("得到的诗句", poetry.toString());
                    poetry.setTranslate("我也是译文");
                    resource.add(poetry);
                }

                SortDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        action();
                    }
                });


            }
        });
    }

    //list
    private void list() {

        //2.创建Request对象
        Request request = new Request.Builder()
                .url(Constant.lcIp + "poetry/get")//设置网络请求的URL地址
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

                resource.clear();
                for(Poetry poetry:list){
                    Log.e("得到的诗句",poetry.toString());
                    poetry.setTranslate("我是译文");
                    resource.add(poetry);
                }

                SortDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        action();
                    }
                });

//                response.close();


            }
        });
    }

    private void action() {
        myAdapter = new MyAdapter<Poetry>(resource, R.layout.item_recommend) {
            @Override
            public void bindView(ViewHolder holder,
                                 Poetry obj) {
                holder.setText(R.id.tv_recommend_name, obj.getName());
                holder.setText(R.id.tv_recommend_author, obj.getAuthor());
                holder.setText(R.id.tv_recommend_content, obj.getContent());
            }
        };
        listView.setAdapter(myAdapter);

    }
}
