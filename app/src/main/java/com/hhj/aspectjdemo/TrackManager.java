package com.hhj.aspectjdemo;

import android.util.Log;

public class TrackManager {
    private final String TAG = this.getClass().getSimpleName();
    private TrackCallback callback;
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

    void init(TrackCallback callback){
        if (callback ==null) {
            return;
        }
        this.callback = callback;
    }

      void onClick(String pageName, String viewName){
        if (callback ==null){
            Log.i(TAG, "onClick: null");
            return;
        }
        callback.onClick(pageName,viewName);
    }


}
