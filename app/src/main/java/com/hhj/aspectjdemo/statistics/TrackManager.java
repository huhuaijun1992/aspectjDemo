package com.hhj.aspectjdemo.statistics;

import android.content.Context;
import android.util.Log;

import com.hhj.aspectjdemo.loginAspect.ILoginFilter;

public class TrackManager {
    private final String TAG = this.getClass().getSimpleName();
    private TrackCallback callback;
    private ILoginFilter iLoginFilter;
    private Context applicationContext ;
    private TrackManager() {
    }

    static  TrackManager manager;
    public static TrackManager getInstance(){
        if (manager ==null){
            synchronized (TrackManager.class){
                if (manager ==null){
                    manager = new TrackManager();
                }
            }
        }
        return  manager;
    }

    public void init(Context applicationContext,TrackCallback callback, ILoginFilter iLoginFilter){
        if (callback ==null || applicationContext ==null||iLoginFilter==null) {
            Log.d(TAG, "init: 初始化失败");
            return;
        }
        this.applicationContext = applicationContext;
        this.callback = callback;
        this.iLoginFilter = iLoginFilter;
    }

      void onClick(String pageName, String viewName){
        if (callback ==null){
            Log.i(TAG, "onClick: null");
            return;
        }
        callback.onClick(pageName,viewName);
    }

    void onCreate(String pageName){
        if (callback ==null){
            return;
        }
        callback.onCreate(pageName);
    }
    void login(int loginDefined){
        if (this.iLoginFilter ==null){
            return;
        }
        this.iLoginFilter.login(applicationContext, loginDefined);
    }

    public ILoginFilter getiLoginFilter() {
        return iLoginFilter;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
