package com.example.a15632.poetrydemo;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class CommunityDetail extends AppCompatActivity {

    private ImageView btn_comment=null;
    private ImageView iv_delete=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉顶部标题栏
        setContentView(R.layout.community_layout);

        findViews();
        MyListener myListener=new MyListener();
        btn_comment.setOnClickListener(myListener);


    }
    public void findViews(){
        btn_comment=findViewById(R.id.btn_comment);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_comment:
                    //弹出评论框
                    showPopupWindow(v);
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




}
