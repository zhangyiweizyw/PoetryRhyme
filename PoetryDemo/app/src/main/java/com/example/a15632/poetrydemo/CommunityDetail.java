package com.example.a15632.poetrydemo;



import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Comment;
import com.example.a15632.poetrydemo.Entity.Community;
import com.example.a15632.poetrydemo.Entity.User;
import com.example.a15632.poetrydemo.util.AddViewsUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommunityDetail extends AppCompatActivity {

    private ImageView btn_comment=null;
    private ImageView iv_delete=null;
    private ImageView iv_like=null;
    private boolean isLike=false;
    private boolean attention=false;
    private boolean isYour=false;
    private boolean isCollect=false;
    private Toolbar titlebar=null;
    private ImageView btn_attention=null;
    private ImageView btn_collect=null;
    private  LinearLayout layout1=null;
    private  LinearLayout layout2=null;
    private Intent intent;
    private Community community;
    private TextView tv_like;
    private TextView tv_comment;

    private TextView tv_title;
    private TextView tv_title2;
    private LinearLayout layout_poem;
    private LinearLayout layout_talk;
    private LinearLayout layout_content;
    private LinearLayout layout_content2;

    private ListView listView=null;
    private MyAdapter<Comment>myAdapter;
    private ArrayList<Comment> comments=new ArrayList<>();
    private TextView tv_author;
    private CircleImageView iv_userhead;

    private AddViewsUtil addViewsUtil=new AddViewsUtil();
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.community_layout);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorback));

        findViews();
        initViews();

        titlebar.setNavigationOnClickListener(new View.OnClickListener() {
            Request request;
            @Override
            public void onClick(View v) {
                finish();
                //当点击返回时，把信息保存会数据库
                Gson gson=new Gson();
                MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
                String jsonStr = gson.toJson(community);//json数据.
                RequestBody body = RequestBody.create(jsonType, jsonStr);
                if(community.getType()==1){
                    //诗词
                    request = new Request.Builder()
                            .url("http://192.168.0.57:8080/MyPoetryRhyme/oripoetry/update")//设置网络请求的URL地址
                            .post(body)
                            .build();
                }
                else{
                    //话题
                    request = new Request.Builder()
                            .url("http://192.168.0.57:8080/MyPoetryRhyme/comm/update")//设置网络请求的URL地址
                            .post(body)
                            .build();
                }
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
                    }
                });
            }
        });
        MyListener myListener=new MyListener();
        btn_comment.setOnClickListener(myListener);
        iv_like.setOnClickListener(myListener);
        btn_attention.setOnClickListener(myListener);
        btn_collect.setOnClickListener(myListener);

        //  updateLikeToEdit();



    }
    public void findViews(){
        btn_comment=findViewById(R.id.btn_comment);
        iv_like=findViewById(R.id.btn_like);
        titlebar=findViewById(R.id.mytoolbar);
        btn_attention=findViewById(R.id.btn_attention);
        btn_collect=findViewById(R.id.btn_collect);
        intent=getIntent();
        boolean isAttention=intent.getBooleanExtra("isAttention",false);
        if(isAttention){
            Drawable drawable=getResources().getDrawable(R.drawable.attention_red);
            btn_attention.setImageDrawable(drawable);
            attention=true;
        }
        layout_poem=findViewById(R.id.layout_poem);
        layout_talk=findViewById(R.id.layout_talk);
        layout_content=findViewById(R.id.layout_content);
        layout_content2=findViewById(R.id.layout_content2);
        community=(Community) intent.getSerializableExtra("community");
        tv_title2=findViewById(R.id.tv_title2);
        tv_title=findViewById(R.id.tv_title);
        tv_author=findViewById(R.id.tv_author);
        iv_userhead=findViewById(R.id.iv_userhead);
        tv_like=findViewById(R.id.tv_likecount);
        tv_comment=findViewById(R.id.tv_commentcount);


    }

    public void initViews(){
        tv_author.setText(community.getUser().getName());
        //iv_userhead.setImageDrawable(getResources().getDrawable(community.getUser().getHeadimg()));
        Log.e("name",community.getUser().getName()+"");
        Log.e("like",community.getLikequantity()+"");
        Log.e("comment",community.getCommentquantity()+"");
        tv_like.setText(community.getLikequantity()+"喜欢");
        tv_comment.setText(community.getCommentquantity()+"评论");
        if(community.getType()==1){
            //原创诗词
            layout_poem.setVisibility(View.VISIBLE);
            layout_talk.setVisibility(View.GONE);
            tv_title.setText(community.getTitle());
            String[]words=addViewsUtil.spiltString(community.getContent());
            for(int index = 0; index < words.length; index++) {
                TextView t=addViewsUtil.addTextView(words[index],CommunityDetail.this);
                layout_content.addView(t);
            }
        }
        else{
            //社区话题
            layout_talk.setVisibility(View.VISIBLE);
            layout_poem.setVisibility(View.GONE);
            tv_title2.setText(community.getTitle());
            TextView t=addViewsUtil.addTextView2("\t\t\t\t"+community.getContent(),CommunityDetail.this);//首行缩进
            layout_content2.addView(t);
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
        tv_comment.setText(community.getCommentquantity()+1+"评论");
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
                            //holder.setImageResource(R.id.imageview, obj.getUser().getHeadimg());
                            holder.setText(R.id.username, obj.getUser().getName());
                            holder.setText(R.id.content, obj.getContent());
                            holder.setText(R.id.tv_date, obj.getDate().toString());
                        }
                    };


                    listView.setAdapter(myAdapter);
                }
                editText.setText("");
                editText.setHint("友善的评论是交流的起点。");
                int count=community.getCommentquantity();
                count=count+1;
                community.setCommentquantity(count);
            }
        });

    }

    //点击喜欢按钮
    public void isFavourite(){
        int count=community.getLikequantity();
        if(isLike){
            //点击按钮变灰
            Drawable drawable=getResources().getDrawable(R.drawable.unlike);
            iv_like.setImageDrawable(drawable);
            isLike=false;
            count=count-1;
            tv_like.setText(count+"喜欢");
        }


        else{
            Drawable drawable=getResources().getDrawable(R.drawable.like_flower);
            iv_like.setImageDrawable(drawable);
            isLike=true;
            count=count+1;
            tv_like.setText(count+"喜欢");
        }
        community.setLikequantity(count);
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
   /* //如果是自己的回答就把喜欢改为修改
    public void updateLikeToEdit(){
        //判断当前页面的用户是否为本人
        //是本人的
        TextView tv_like=findViewById(R.id.tv_likecount);
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

    }*/








}
