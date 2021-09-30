package com.hhj.aspectjdemo.statistics;

import android.content.Context;
import android.view.View;

import com.hhj.aspectjdemo.loginAspect.ILoginFilter;
import com.hhj.aspectjdemo.loginAspect.LoginFilter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TrackAspect {
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public  void onClickPointcut(){}

//    @Pointcut("execution(* com.hhj.aspectjdemo.actvity.BaseActivity.onCreate(..))")
    @Pointcut("execution(* com.hhj.aspectjdemo.actvity.*Activity.onCreate(..))")
    public void onActivityCreate(){}

    @Pointcut("execution(@com.hhj.aspectjdemo.loginAspect.LoginFilter * *(..))")
//    @Pointcut("execution(@com.hhj.aspectjdemo.loginAspect.LoginFilter * *(..))")
    public void loginFilter(){}


    @Around("onClickPointcut()")
    public void onClick(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String className = "";
        if (target != null) {
            className = target.getClass().getName();
        }
        //获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof View) {
            View view = (View) args[0];
            String entryName = view.getResources().getResourceEntryName(view.getId());
            TrackManager.getInstance().onClick(className, entryName);
        }
        //执行源代码
        joinPoint.proceed();
    }

    @Before("onActivityCreate()")
    public void onCreate(JoinPoint joinPoint) throws Throwable{
        Object target = joinPoint.getTarget();
        String className = "";
        if (target != null) {
            className =  target.getClass().getName();
            TrackManager.getInstance().onCreate(className);
        }
    }

    @Around("loginFilter()")
    public void login(ProceedingJoinPoint joinPoint) throws Throwable {
        ILoginFilter iLoginFilter = TrackManager.getInstance().getiLoginFilter();
        Context context = TrackManager.getInstance().getApplicationContext();
        if (iLoginFilter == null){
            return;
        }
        Signature signature =  joinPoint.getSignature();
        if (!(signature instanceof MemberSignature)){
            throw  new RuntimeException("该注解只能用于方法上");
        }
        MethodSignature signatureMethod = (MethodSignature) signature;
        LoginFilter filter = signatureMethod.getMethod().getAnnotation(LoginFilter.class);
        if (filter ==null){
            return;
        }
        if (iLoginFilter.isLogin(context)){
            joinPoint.proceed();
        }else{
            iLoginFilter.login(context,filter.loginDefined());
        }

    }
}
