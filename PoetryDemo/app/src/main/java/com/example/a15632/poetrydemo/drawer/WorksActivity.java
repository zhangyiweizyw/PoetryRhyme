package com.example.a15632.poetrydemo.drawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.a15632.poetrydemo.Entity.Works;
import com.example.a15632.poetrydemo.MyAdapter;
import com.example.a15632.poetrydemo.R;

import java.util.ArrayList;

public class WorksActivity extends AppCompatActivity {
    private ImageView imageView;
    private GridView gridView;
    private ArrayList<Works> works=new ArrayList<>();
    private MyAdapter<Works> myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.activity_works);
        initData();
        findView();
        action();
    }

    private void initData() {
        works.add(new Works(1,"多情却被无情抛","自古多情空余恨","2015-09-28"));
        works.add(new Works(2,"欲买桂花同载酒","切酩酊，任他两轮日月，来往穿梭","2017-09-28"));
        works.add(new Works(3,"秋风清","相知相见知何年，此时此夜难为情","2015-09-28"));
        works.add(new Works(4,"二十余年如一梦","人生在世，有欲有恨，烦恼为苦，解脱为乐","2015-09-28"));
    }
    private void  findView() {

        gridView=findViewById(R.id.gv_works);
        imageView=findViewById(R.id.img_works_back);
    }
    private void action() {
        //返回键
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //gridview
        myAdapter=new MyAdapter<Works>(works,R.layout.item_show_works) {
            @Override
            public void bindView(ViewHolder holder, Works obj) {
                holder.setText(R.id.item_works_title,obj.getTitle());
                holder.setText(R.id.item_works_content,obj.getContent());
                holder.setText(R.id.item_works_date,obj.getDate());
            }
        };
        gridView.setAdapter(myAdapter);
    }
}
