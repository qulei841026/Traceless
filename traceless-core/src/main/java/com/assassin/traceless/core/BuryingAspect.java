package com.assassin.traceless.core;

import android.util.Log;

import com.assassin.traceless.annotations.weaving.Burying;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BuryingAspect {

    static final String TAG = "BuryingAspect";

    private static final String ACTION_REPORT =
            "@com.assassin.traceless.annotations.weaving.Burying";

    @Pointcut("within(" + ACTION_REPORT + " *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    @Pointcut("execution(" + ACTION_REPORT + " * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Pointcut("execution(" + ACTION_REPORT + " *.new(..)) || constructorInsideAnnotatedType()")
    public void constructor() {
    }

    @Around("(method() || constructor()) && @annotation(burying)")
    public Object execute(ProceedingJoinPoint joinPoint, Burying burying) throws Throwable {
        Log.d(TAG, "[execute]->" + joinPoint.getSignature().toString());
        Log.d(TAG, "[value]->" + burying.value());
        return joinPoint.proceed();
    }
}