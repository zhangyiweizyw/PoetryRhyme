package com.example.a15632.poetrydemo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a15632.poetrydemo.Entity.Poetry;
import com.example.a15632.poetrydemo.util.AddViewsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoemTranslate extends Fragment {

    private View fragment;

    private Intent intent;
    private LinearLayout layout_translate;
    private Poetry poetry;
    private AddViewsUtil addViewsUtil=new AddViewsUtil();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.tab_poem_translate, container, false);
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
        intent=getActivity().getIntent();
        layout_translate=fragment.findViewById(R.id.layout_translate);
        poetry=(Poetry)intent.getSerializableExtra("poem");

    }
    private void initViews(){

        String translates[]=addViewsUtil.spiltString(poetry.getTranslate());
        for(int i=0;i<translates.length;i++){
            TextView textView=addViewsUtil.addTextView4(translates[i],getContext());
            layout_translate.addView(textView);
        }
    }

}
