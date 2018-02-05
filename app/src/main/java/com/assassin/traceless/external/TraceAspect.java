package com.assassin.traceless.external;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Le-q on 2018/1/29.
 */
@Aspect
public class TraceAspect {

    private static final String TAG = "qulei";

    @Before("execution(* android.app.Activity.on**(..))")
    public void methodBefore1(JoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "TraceAspect [Before]->" + joinPoint.getSignature().toString());
    }

    @After("execution(* com.assassin.traceless.LogoActivity.fetchData(..))")
    public void methodAfter1(JoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "TraceAspect [After]->" + joinPoint.getSignature().toString());
    }

    @Pointcut("call(* com.assassin.traceless.logic.Photo.show(..))")
    public void methodCall1() {
        Log.d(TAG, "TraceAspect [methodCall]");
    }

    @Before("methodCall1()")
    public void methodAfter2(JoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "TraceAspect [Before]->" + joinPoint.getSignature().toString());
    }

    @Around("execution(* com.assassin.traceless.LogoActivity.onCall(..))")
    public void methodAround1(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "TraceAspect [Around]->" + joinPoint.getSignature().toString());
        joinPoint.proceed();
    }

    @Around("call(* com.assassin.traceless.logic.Collections.fetchCollections(..))")
    public void methodAround2(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "TraceAspect [Around]->" + joinPoint.getSignature().toString());
        joinPoint.proceed();
    }

    @Around("execution (* com.assassin.traceless.logic.Collections.testAbc(..))")
    public void methodAround3(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "TraceAspect [Around]->" + joinPoint.getSignature().toString());
        joinPoint.proceed();
    }


}
