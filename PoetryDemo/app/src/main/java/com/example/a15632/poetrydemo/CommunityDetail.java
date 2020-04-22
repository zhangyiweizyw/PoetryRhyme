package com.example.a15632.poetrydemo;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.community_layout);

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
        final PopupWindow popupWindow=new PopupWindow(popupWindowView,ActionBar.LayoutParams.MATCH_PARENT ,2000, true);
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
        //引入依附的布局
        View parentView = LayoutInflater.from(CommunityDetail.this).inflate(R.layout.comment_popupwindow, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        //点击叉号按钮
        iv_delete=popupWindowView.findViewById( R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommunityDetail.this,"返回",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
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
                /*Intent intent=new Intent(CommunityDetail.this,MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);*/
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
