package com.assassin.traceless.external;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Le-q on 2018/2/6.
 */
@Aspect
public class FieldAspect {

    private static final String TAG = "qulei";

    @Around("get(String com.assassin.traceless.logic.User.name)")
    public String fieldAroundGet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "FieldAspect [Around-Get]->" + joinPoint.getSignature().getName());
        return (String) joinPoint.proceed();
    }

    @Around("set(String com.assassin.traceless.logic.User.name)")
    public void fieldAroundSet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "FieldAspect [Around-Set]->" + joinPoint.getSignature().getName());
    }
}
