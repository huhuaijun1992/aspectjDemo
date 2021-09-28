package com.hhj.aspectjdemo;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    private static final String TAG =  Application.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        TrackManager.getInstance().init(new TrackCallback() {
            @Override
            public void onClick(String pageName, String viewName) {
                Log.d(TAG, "onClick: 点击事件拦截了");
            }
        });
    }
}
