package com.example.a15632.poetrydemo;



import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a15632.poetrydemo.Entity.Poetry;
import com.example.a15632.poetrydemo.util.AddViewsUtil;

public class PoemContent extends Fragment {

    private TextView tv_author;
    private TextView tv_title;
    private LinearLayout layout_content;
    private Intent intent;
    private static final String APP_ID = "20200516000457247";
    private static final String SECURITY_KEY = "MRyaVMjtisEE4hryRWb_";
    private final String request = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private Poetry poetry;
    private String text;
    private AddViewsUtil addViewsUtil=new AddViewsUtil();



    private View fragment;
    private ImageView btn_language;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.tab_poem_content, container, false);
        //code begin
        findViews();
        initViews();







        //code end
        ViewGroup p = (ViewGroup) fragment.getParent();
        if (p != null) {
            p.removeView(fragment);
        }

        return fragment;
    }



    private void findViews(){
        btn_language=fragment.findViewById(R.id.languane);
        tv_title=fragment.findViewById(R.id.tv_title);
        tv_author=fragment.findViewById(R.id.tv_author);
        layout_content=fragment.findViewById(R.id.layout_content);
        intent=getActivity().getIntent();
        poetry=(Poetry)intent.getSerializableExtra("poem");
    }
    private void initViews(){
        tv_title.setText(poetry.getName());
        tv_author.setText(poetry.getAuthor());
        String[]words=addViewsUtil.spiltString(poetry.getContent());
        for(int index = 0; index < words.length; index++) {
            TextView t=addViewsUtil.addTextView3(words[index],getContext());
            layout_content.addView(t);;
        }
    }
}
