package com.example.a15632.poetrydemo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Comment;
import com.example.a15632.poetrydemo.Entity.Msg;
import com.example.a15632.poetrydemo.Entity.User;
import com.jaeger.library.StatusBarUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class CommunityDetail extends AppCompatActivity {

    private ImageView btn_comment=null;
    private ImageView iv_delete=null;
    private ImageView iv_like=null;
    private boolean isLike=false;
    private boolean attention=false;
    private boolean isYour=false;
    private boolean isCollect=false;
    private TitleLayout titlebar=null;
    private ImageView btn_attention=null;
    private ImageView btn_collect=null;
    private  LinearLayout layout1=null;
    private  LinearLayout layout2=null;

    private ListView listView=null;
    private MyAdapter<Comment>myAdapter;
    private ArrayList<Comment> comments=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.community_layout);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorback));

        findViews();
        MyListener myListener=new MyListener();
        btn_comment.setOnClickListener(myListener);
        iv_like.setOnClickListener(myListener);
        btn_attention.setOnClickListener(myListener);
        btn_collect.setOnClickListener(myListener);

        updateLikeToEdit();

        clickTitle();


    }
    public void findViews(){
        btn_comment=findViewById(R.id.btn_comment);
        iv_like=findViewById(R.id.btn_like);
        titlebar=findViewById(R.id.title_bar);
        btn_attention=findViewById(R.id.btn_attention);
        btn_collect=findViewById(R.id.btn_collect);
        Intent intent=this.getIntent();
        boolean isAttention=intent.getBooleanExtra("isAttention",false);
        if(isAttention){
            Drawable drawable=getResources().getDrawable(R.drawable.attention_red);
            btn_attention.setImageDrawable(drawable);
            attention=true;
        }

    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_comment:
                    //弹出评论框
                    showPopupWindow(v);
                    break;
                case R.id.btn_like:
                    if(isYour){
                        //是本人执行修改回答方法
                    }
                    else{
                        //不是本人的，则执行喜欢方法
                        //喜欢
                        isFavourite();
                    }
                    break;
                case R.id.btn_attention:
                    //关注
                    isAttention();
                    break;
                case R.id.btn_collect:
                    //收藏
                    clickCollect();
                    break;
            }
        }
    }


    //显示弹窗
    private void showPopupWindow(View v){
        View popupWindowView=getLayoutInflater().inflate(R.layout.comment_popupwindow,null);
        final PopupWindow popupWindow=new PopupWindow(popupWindowView,ActionBar.LayoutParams.MATCH_PARENT ,ActionBar.LayoutParams.MATCH_PARENT, true);


        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        //弹出动画
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        View parentView = LayoutInflater.from(CommunityDetail.this).inflate(R.layout.comment_popupwindow, null);
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        iv_delete=popupWindowView.findViewById( R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommunityDetail.this,"返回",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        layout1=popupWindowView.findViewById(R.id.comment_no);
        layout2=popupWindowView.findViewById(R.id.layout_list);
        listView=popupWindowView.findViewById(R.id.lv_data);
        //添加评论
        addComment(popupWindowView);


    }
    private void addComment(final View popupview){
        comments.clear();
        final EditText editText=popupview.findViewById(R.id.edit_comment);
        TextView tv_publish=popupview.findViewById(R.id.tv_publish);

        tv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText())) {
                    Toast.makeText(v.getContext(), "请输入评论", Toast.LENGTH_SHORT).show();
                } else {
                    String content = editText.getText().toString();
                    User u = new User("张三", "123456", R.drawable.default_headimg);
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    Comment comment = new Comment(u, content, date);
                    comments.add(comment);
                    if(comments!=null){
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                    }
                    myAdapter = new MyAdapter<Comment>(comments, R.layout.item_comment) {
                        @Override
                        public void bindView(ViewHolder holder, Comment obj) {
                            holder.setImageResource(R.id.imageview, obj.getUser().getHeadimg());
                            holder.setText(R.id.username, obj.getUser().getUsername());
                            holder.setText(R.id.content, obj.getContent());
                            holder.setText(R.id.tv_date, obj.getDate().toString());
                        }
                    };


                    listView.setAdapter(myAdapter);
                }
                editText.setText("");
                editText.setHint("友善的评论是交流的起点。");
            }
        });

    }

    //点击喜欢按钮
    public void isFavourite(){
        if(isLike){
            //点击按钮变灰
            Drawable drawable=getResources().getDrawable(R.drawable.unlike);
            iv_like.setImageDrawable(drawable);
            isLike=false;
        }


        else{
            Drawable drawable=getResources().getDrawable(R.drawable.like_flower);
            iv_like.setImageDrawable(drawable);
            isLike=true;
        }
    }
    //点击关注按钮
    public void isAttention(){
        if(attention){
            //点击按钮变灰
            Drawable drawable=getResources().getDrawable(R.drawable.attention_gray2);
            btn_attention.setImageDrawable(drawable);
            attention=false;
        }


        else{
            Drawable drawable=getResources().getDrawable(R.drawable.attention_red);
            btn_attention.setImageDrawable(drawable);
            attention=true;
        }
    }
    //点击收藏按钮
    public void clickCollect(){
        if(isCollect){
            //点击按钮变灰
            Drawable drawable=getResources().getDrawable(R.drawable.uncollect);
            btn_collect.setImageDrawable(drawable);
            isCollect=false;
        }


        else{
            Drawable drawable=getResources().getDrawable(R.drawable.collect1);
            btn_collect.setImageDrawable(drawable);
            isCollect=true;
        }
    }

    //标题栏的点击事件
    public void clickTitle(){
        titlebar.setLeftIconOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //跳转社区首页

                finish();
            }
        });

    }

    //如果是自己的回答就把喜欢改为修改
    public void updateLikeToEdit(){
        //判断当前页面的用户是否为本人
        //是本人的
        TextView tv_like=findViewById(R.id.tv_like);
        if(isYour){
            Drawable drawable=getResources().getDrawable(R.drawable.toedit);
            iv_like.setImageDrawable(drawable);
            tv_like.setText("修改回答");
        }
        else{
            Drawable drawable=getResources().getDrawable(R.drawable.unlike);
            iv_like.setImageDrawable(drawable);
            tv_like.setText("xxx喜欢");
        }

    }






}
