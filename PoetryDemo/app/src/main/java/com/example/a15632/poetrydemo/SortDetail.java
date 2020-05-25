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
    private static String ip = "http://192.168.0.103:8080/PoetryRhyme/";
    private OkHttpClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sort);
        client = new OkHttpClient();
        Intent intent = getIntent();
        str = intent.getStringExtra("sort");
        findView();
        search(str);
        setListener();

    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SortDetail.this,PoemDetail.class);
                intent.putExtra("poem",resource.get(position));
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
                .url(ip + "poetry/search")
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

                List<Poetry> list = new Gson().fromJson(jsonStr,new TypeToken<List<Poetry>>(){}.getType());
                resource.clear();
                for(Poetry poetry:list){
                    Log.e("得到的诗句",poetry.toString());
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

    private void action() {
        myAdapter = new MyAdapter<Poetry>(resource, R.layout.item_recommend) {
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
