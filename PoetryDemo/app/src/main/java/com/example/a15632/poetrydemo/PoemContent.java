package com.example.a15632.poetrydemo;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a15632.poetrydemo.Entity.Poetry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoemContent extends Fragment {

    private TextView tv_author;
    private TextView tv_title;
    private LinearLayout layout_content;
    private Intent intent;
    private Poetry poetry;



    private View fragment;
    private ImageView btn_language;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragment = inflater.inflate(R.layout.tab_poem_content, container, false);
        //code begin
        findViews();
        initViews();

        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // makeTranslate();
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
        String[]words=spiltString(poetry.getContent());
        for(int index = 0; index < words.length; index++) {
            addTextView(words[index]);
        }
    }

    private String[] spiltString(String str){
        /*正则表达式：句子结束符*/
        String regEx="[`~!|':;',\\\\\\\\[\\\\\\\\].<>/?~！;‘；：”“’。，、？|-]";
        Pattern p =Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        /*按照句子结束符分割句子*/
        String[] words = p.split(str);
        /*将句子结束符连接到相应的句子后*/
        if(words.length > 0) {
            int count = 0;
            while(count < words.length) {
                if(m.find()) {
                    words[count] += m.group();
                }
                count++;
            }
        }
        return words;
    }
    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addTextView(String text){
        TextView textView=new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(18);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(40, 10, 0, 0);
        textView.setLayoutParams(layoutParams);
        // textView.setLetterSpacing(0.09f);//字间距
        textView.setScaleX(1.0f);
        textView.setLineHeight(8);//行间距
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.SERIF);//字体样式
        layout_content.addView(textView);
    }










}
