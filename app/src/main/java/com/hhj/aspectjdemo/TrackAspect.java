package com.hhj.aspectjdemo;

import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TrackAspect {
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public  void onClickPointcut(){}

    @Around("onClickPointcut()")
    public void onClick(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String className = "";
        if (target != null) {
            className = target.getClass().getName();
        }
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof View) {
            View view = (View) args[0];
            String entryName = view.getResources().getResourceEntryName(view.getId());
            TrackManager.getInstance().onClick(className, entryName);
        }
        joinPoint.proceed();
    }
}
