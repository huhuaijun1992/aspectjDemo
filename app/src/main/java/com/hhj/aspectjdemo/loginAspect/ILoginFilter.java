package com.hhj.aspectjdemo.loginAspect;

import android.content.Context;

public interface ILoginFilter {
    void login(Context applicationContext, int loginDefine);
    boolean isLogin(Context applicationContext);
    void clearLoginState(Context context);
}
