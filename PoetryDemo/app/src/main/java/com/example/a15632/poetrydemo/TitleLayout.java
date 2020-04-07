package com.example.a15632.poetrydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleLayout extends LinearLayout {

    private ImageView leftback;
    private TextView tvtitle;
    private ImageView ivtip;
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
       /* LayoutInflater.from(context).inflate(R.layout.layout_title,this);
        ImageView leftback=findViewById(R.id.left_icon);
        ImageView addcommunity=findViewById(R.id.addcommunity);
        leftback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        addcommunity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"跳转添加社区页面",Toast.LENGTH_SHORT).show();
            }
        });*/
       initView(context,attrs);
    }
    //初始化视图
    private void initView(final Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_title, this);
        leftback=inflate.findViewById(R.id.left_icon);
        tvtitle = inflate.findViewById(R.id.tv_title);
        ivtip=inflate.findViewById(R.id.addcommunity);

        init(context,attributeSet);

        /*leftback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回社区首页
                Intent intent=new Intent(getContext(),CommunityFragment.class);
                getContext().startActivity(intent);
            }
        });*/

        ivtip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转新建社区话题页面
            }
        });
    }

    //初始化资源文件
    public void init(Context context, AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTitleBar);
        String title = typedArray.getString(R.styleable.CustomTitleBar_title);//标题
        int leftIcon = typedArray.getResourceId(R.styleable.CustomTitleBar_left_icon, R.drawable.backto);//左边图片
        int rightIcon = typedArray.getResourceId(R.styleable.CustomTitleBar_right_icon, R.drawable.addtip2);//右边图片
        int titleBarType = typedArray.getInt(R.styleable.CustomTitleBar_titlebar_type, 10);//标题栏类型,默认为10

        //赋值进去我们的标题栏
        tvtitle.setText(title);
        leftback.setImageResource(leftIcon);
        ivtip.setImageResource(rightIcon);

        //可以传入type值,可自定义判断值
        if(titleBarType == 10){//不传入,默认为10,显示更多 文字,隐藏更多图标按钮
            leftback.setVisibility(View.GONE);
            //ivtip.setVisibility(View.GONE);

        }else if(titleBarType == 11){//传入11,显示更多图标按钮,隐藏更多 文字，显示创作文字，隐藏添加按钮
          //  leftback.setVisibility(View.GONE);
            ivtip.setVisibility(View.GONE);
        }else if(titleBarType==12){
            leftback.setVisibility(View.GONE);
            ivtip.setVisibility(View.GONE);
        }
    }
}
