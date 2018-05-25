package com.assassin.traceless.core;

import android.util.Log;

import com.assassin.traceless.annotations.weaving.Tracking;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * TrackingAspect
 * Created by Qulit on 2018/3/19.
 */
@Aspect
public class TrackingAspect {

    private static final String TAG = "TrackingAspect";

    private static final String ANNOTATION_CLASS =
            "@com.assassin.traceless.annotations.weaving.Tracking";

    @Pointcut("execution(" + ANNOTATION_CLASS + " * *(..))")
    public void method() {
    }

    @Pointcut("execution(" + ANNOTATION_CLASS + " *.new(..))")
    public void constructor() {
    }

    @Around("(method() || constructor()) && @annotation(tracking)")
    public Object execute(ProceedingJoinPoint joinPoint, Tracking tracking) throws Throwable {
        String tag = tracking.tag();
        Log.d(tag, "[" + TAG + "-ExecuteMethod]->" + joinPoint.getSignature().toString());
        String method = joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName();
        StackTraceElement[] element = new Throwable().getStackTrace();
        boolean isFind = false;
        String sMethod;
        for (StackTraceElement item : element) {
            sMethod = item.getClassName() + "." + item.getMethodName();
            if (!isFind) {
                isFind = method.equals(sMethod);
            } else {
                Log.d(tag, "[" + TAG + "-StackTraceElement]->" + item);
            }
        }
        return joinPoint.proceed();
    }
}