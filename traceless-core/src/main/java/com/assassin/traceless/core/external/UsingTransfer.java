package com.assassin.traceless.core.external;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.SourceLocation;

/**
 * UsingTransfer
 * Created by Qulit on 2018/3/1.
 */
public class UsingTransfer {

    private static final String GET = "field-get";
    private static final String SET = "field-set";

    private Interceptor interceptor = null;

    private static UsingTransfer instance = new UsingTransfer();

    private UsingTransfer() {
    }

    public static UsingTransfer getInstance() {
        return instance;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void removeInterceptor() {
        this.interceptor = null;
    }

    public static Object process(String tag, final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result = joinPoint.proceed();
        String follow = joinPoint.getSignature().toString();
        SourceLocation location = joinPoint.getSourceLocation();
        String using = location.getWithinType().getName()
                + "(" + location.getFileName() + ":" + location.getLine() + ")";
        String kind = joinPoint.getKind();
        if (GET.equals(kind)) {
            println(follow, kind, tag, using, result);
        } else if (SET.equals(kind)) {
            println(follow, kind, tag, using, joinPoint.getArgs()[0]);
        }
        return result;
    }

    private static void println(String follow, String kind, String tag, String using, final Object o) {
        Interceptor interceptor = UsingTransfer.getInstance().interceptor;
        if (interceptor == null || !interceptor.hold(follow, kind, tag, using, o)) {
            Log.d(tag, "Follow = " + follow + "\n"
                    + "Using = " + using + "\n"
                    + kind + " value = " + o);
        }
    }

    public interface Interceptor {
        boolean hold(String follow, String kind, String tag, String using, final Object o);
    }

}
