package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class Search extends AppCompatActivity {
    private EditText input;
    private TextView cancel;
    private ListView listView;

    private ArrayList<Poetry> resource = new ArrayList<>();
    private MyAdapter<Poetry> myAdapter;

    //  交互
    private static String ip = "http://192.168.0.103:8080/PoetryRhyme/";
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_search);
        client = new OkHttpClient();
        findView();
        setListener();

    }


    //search
    private void search(TextView v) {

        Log.e("开启", "运行");
        String jsonStr = null;
        jsonStr = new Gson().toJson(v.getText().toString());

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

                Search.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        action();
                    }
                });


            }
        });
    }

    private void findView() {
        input=findViewById(R.id.et_home_input);
        cancel=findViewById(R.id.tv_back_to_home);
        listView = findViewById(R.id.lv_search);

    }

    private void setListener() {
        //关于EditView的操作,相应虚拟键盘上的"搜索"按钮
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search(v);
                return true;
            }
        });
        //点击取消后返回首页
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("取消","执行");
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(),PoemDetail.class);
                intent.putExtra("poem",resource.get(position));
                startActivity(intent);
            }
        });
    }

    private void action(){
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
