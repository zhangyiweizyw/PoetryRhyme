package com.example.a15632.poetrydemo;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15632.poetrydemo.Entity.Poetry;
import com.example.a15632.poetrydemo.util.AddViewsUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoemWrite extends Fragment {

    private View fragment;
    private Intent intent;
    private LinearLayout layout_content;

    private int index=0;
    private List<EditText> editTextList=new ArrayList<>();

    private Poetry poetry;
    private TextView tv_title;
    private TextView tv_author;
    private FloatingActionButton floatingActionButton;

    private String write;
    private String answer;
    private AddViewsUtil addViewsUtil=new AddViewsUtil();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.tab_poem_write, container, false);
        //code begin
        findViews();
        initViews();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        //code end
        ViewGroup p = (ViewGroup) fragment.getParent();
        if (p != null) {
            p.removeView(fragment);
        }

        return fragment;
    }
    private void findViews(){
        intent=getActivity().getIntent();
        poetry=(Poetry) intent.getSerializableExtra("poem");
        tv_author=fragment.findViewById(R.id.tv_author);
        tv_title=fragment.findViewById(R.id.tv_title);
        layout_content=fragment.findViewById(R.id.layout_content);
        floatingActionButton=fragment.findViewById(R.id.fab);
    }

    private void initViews(){
        tv_title.setText(poetry.getName());
        tv_author.setText(poetry.getAuthor());
        int length=addViewsUtil.spiltString2(poetry.getContent(),answer);
        for(int i=0;i<length;i++){
            EditText editText=addViewsUtil.addEditText(getContext(),getActivity(),editTextList);
            layout_content.addView(editText);
        }

    }

    private void showPopupMenu(View v) {
        final PopupMenu popupMenu
                = new PopupMenu(fragment.getContext(), v);
        popupMenu.getMenuInflater()
                .inflate(R.menu.floatbutton, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(fragment.getContext(),
                                item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.item_seeanswer:
                                showPopupWindow(item.getActionView());
                                break;
                            case R.id.item_submit:
                                for(int i=0;i<editTextList.size();i++){
                                    write=write+editTextList.get(i).getText().toString();
                                }
                                Log.e("name","name"+editTextList.get(0).getText().toString());
                                if(write.equals(answer)){
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("您已经背会这首诗了！")
                                            .setNegativeButton("确定", null)
                                            .show();
                                }
                                else if(write.length()==0){
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("请先默写再提交")
                                            .setNegativeButton("默写", null)
                                            .show();
                                }
                                else{
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("默写有错误，再检查一下吧！")
                                            .setNegativeButton("检查", null)
                                            .show();
                                }
                                break;
                        }
                        return false;
                    }
                }
        );
        popupMenu.show();

    }
    private void showPopupWindow(View v){
        View popupWindowView=getLayoutInflater().inflate(R.layout.answer_popupwindow,null);
        final PopupWindow popupWindow=new PopupWindow(popupWindowView, ActionBar.LayoutParams.MATCH_PARENT ,600, true);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp =getActivity(). getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //弹出动画
        popupWindow.setAnimationStyle(R.style.take_photo_anim);
        View parentView = LayoutInflater.from(getContext()).inflate(R.layout.answer_popupwindow, null);
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        TextView textView=popupWindowView.findViewById(R.id.tv_answer);
        textView.setText(poetry.getContent());
    }
}
