package com.hhj.aspectjdemo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

import com.hhj.aspectjdemo.actvity.LoginActivity;
import com.hhj.aspectjdemo.loginAspect.ILoginFilter;
import com.hhj.aspectjdemo.statistics.TrackCallback;
import com.hhj.aspectjdemo.statistics.TrackManager;

/**
 * @author hhj
 */
public class MyApplication extends MultiDexApplication {
    private static final String TAG =  Application.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        TrackManager.getInstance().init( this,new TrackCallback() {
            @Override
            public void onClick(String pageName, String viewName) {
                Log.d(TAG, "onClick: 点击事件拦截了");
            }

            @Override
            public void onCreate(String pageName) {
                Log.d(TAG, "onCreate: 页面被创建了"+ pageName);
            }
        },new ILoginFilter() {
            @Override
            public void login(Context applicationContext, int loginDefine) {
                switch (loginDefine) {
                    case 0:
                        Intent intent = new Intent(applicationContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(applicationContext, "您还没有登录，请登陆后执行！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(applicationContext, "执行失败，因为您还没有登录！", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public boolean isLogin(Context applicationContext) {
                //此处控制获取登录信息
                Log.d(TAG, "isLogin: false");
                return true;
            }

            @Override
            public void clearLoginState(Context context) {
                Log.d(TAG, "clearLoginState: 数据清除");
            }
        });

    }
}
