package com.example.a15632.poetrydemo.talkingroom;

import android.app.Application;


import java.lang.reflect.Method;

public class MyApplication extends Application {
    public static Application		mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
    }
}
