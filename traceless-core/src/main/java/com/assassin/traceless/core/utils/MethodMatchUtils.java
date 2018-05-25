package com.assassin.traceless.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * MethodMatchUtils
 * Created by Qulit on 2018/5/22.
 */
public class MethodMatchUtils {

    public static String methodNameMaker(final ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            StringBuilder name = new StringBuilder();
            name.append(methodSignature.getReturnType().toString()).append(" ");     //函数返回类型
            name.append(methodSignature.getDeclaringTypeName()); //类名(包路径)
            name.append(".").append(methodSignature.getName());
            name.append("(");
            Class[] c = methodSignature.getParameterTypes();
            if (c != null && c.length > 0) {
                for (int i = 0, size = c.length; i < size; i++) {
                    name.append(c[i].getCanonicalName());
                    name.append(i != size - 1 ? "," : "");
                }
            }
            name.append(")");
            return name.toString();
        } else {
            return "";
        }
    }

}
