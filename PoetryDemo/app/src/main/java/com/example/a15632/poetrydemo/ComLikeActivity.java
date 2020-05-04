package com.example.a15632.poetrydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.a15632.poetrydemo.Entity.ComAndLike;
import com.example.a15632.poetrydemo.Entity.Community;
import com.example.a15632.poetrydemo.Entity.User;

import java.sql.Date;
import java.util.ArrayList;

public class ComLikeActivity extends AppCompatActivity {

    private LinearLayout layout;
    private LinearLayout layout1;
    private ListView listView;
    private MyAdapter<ComAndLike> myAdapter;
    private ArrayList<ComAndLike>comAndLikes=new ArrayList<>();
    private TitleLayout titleLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.edit_comlike);

        findViews();
        initViews();
        clickTitle();

    }

    private void findViews(){
        layout=findViewById(R.id.comlike_no);
        layout1=findViewById(R.id.list_no);
        listView=findViewById(R.id.lv_data);
        titleLayout=findViewById(R.id.toolbar);
    }
    private void initViews(){
        layout.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        User u = new User("李四","123446", R.drawable.default_head);
        Community c=new Community("静夜思","床前明月光，疑是地上霜。举头望明月，低头思故乡。",100,100,date);
        ComAndLike cal=new ComAndLike(u,date,c);
        comAndLikes.add(cal);
        myAdapter=new MyAdapter<ComAndLike>(comAndLikes, R.layout.item_comlike) {
            @Override
            public void bindView(ViewHolder holder, ComAndLike obj) {
                holder.setImageResource(R.id.imageview,obj.getU().getHeadimg());
                holder.setText(R.id.username,obj.getU().getUsername());
                holder.setText(R.id.tv_date,obj.getDate().toString());
                holder.setText(R.id.tv_content,obj.getCommunity().getContent());
            }
        };
        listView.setAdapter(myAdapter);

    }
    //标题栏的点击事件
    public void clickTitle(){
        titleLayout.setLeftIconOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
