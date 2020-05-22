package com.example.a15632.poetrydemo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a15632.poetrydemo.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddViewsUtil {

    public String[] spiltString(String str){
        /*正则表达式：句子结束符*/
        String regEx="[`~!|':;',\\\\\\\\[\\\\\\\\].<>/?~！;‘；：”“’。，、？|-]";
        Pattern p =Pattern.compile(regEx);
        Matcher m = p.matcher(str);
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
    public TextView addTextView(String text,Context context){
        TextView textView=new TextView(context);
        textView.setText(text);
        textView.setTextSize(17);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(40, 10, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setScaleX(1.0f);
        textView.setLineHeight(8);//行间距
        textView.setGravity(Gravity.CENTER);
        return textView;

    }
    public TextView addTextView2(String text, Context context){
        TextView textView=new TextView(context);
        textView.setText(text);
        textView.setTextSize(17);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 0, 30, 0);
        textView.setLayoutParams(layoutParams);
        // textView.setScaleX(1.0f);
        textView.setLineHeight(100);//行间距
        textView.setGravity(Gravity.LEFT|Gravity.RIGHT);
        return textView;

    }
    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextView addTextView3(String text,Context context){
        TextView textView=new TextView(context);
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
        return textView;

    }
    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextView addTextView4(String text,Context context){
        TextView textView=new TextView(context);
        textView.setText(text);
        textView.setTextSize(18);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,0,0);
        textView.setLayoutParams(layoutParams);
        // textView.setLetterSpacing(0.09f);//字间距
        textView.setScaleX(1.0f);
        textView.setTypeface(Typeface.SERIF);//字体样式
        return textView;

    }
    public int spiltString2(String str,String str2){
        /*正则表达式：句子结束符*/
        String regEx="[`~!|':;',\\\\\\\\[\\\\\\\\].<>/?~！;‘；：”“’。，、？|-]";
        Pattern p =Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        /*按照句子结束符分割句子*/
        String[] words = p.split(str);
        for(int i=0;i<words.length;i++) {
            str2 = str2 + words[i];
        }
        return words.length;
    }

    public EditText addEditText(Context context, Activity activity, List<EditText>editTextList){
        final EditText editText=new EditText(context);
        editText.setTextSize(18);
        editText.setTextScaleX(1.0f);
        Drawable drawable=activity.getResources().getDrawable(R.drawable.edittext_selector);
        editText.setBackground(drawable);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(480, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);
        editText.setLayoutParams(layoutParams);
        editTextList.add(editText);
        return editText;
    }
}
