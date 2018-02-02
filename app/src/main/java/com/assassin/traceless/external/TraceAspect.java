package com.assassin.traceless.external;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by Le-q on 2018/1/29.
 */
@Aspect
public class TraceAspect {

    //ydc start
    private static final String TAG = "qulei";

    @Before("execution(* android.app.Activity.on**(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.d(TAG, "onActivityMethodBefore: " + key + "\n" + joinPoint.getThis());

    }

}
