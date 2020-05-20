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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoemTranslate extends Fragment {

    private View fragment;

    private Intent intent;
    private LinearLayout layout_translate;
    private Poetry poetry;
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

        String translates[]=spiltString(poetry.getTranslate());
        for(int i=0;i<translates.length;i++){
            addTextView(translates[i]);
        }
    }
    private String[] spiltString(String str){
        /*正则表达式：句子结束符*/
        String regEx="[`~!|':;'\\\\\\\\[\\\\\\\\].<>/?~！;‘；：”“’。？|-]";
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,0,0);
        textView.setLayoutParams(layoutParams);
        // textView.setLetterSpacing(0.09f);//字间距
        textView.setScaleX(1.0f);
        textView.setTypeface(Typeface.SERIF);//字体样式
        layout_translate.addView(textView);
    }
}
