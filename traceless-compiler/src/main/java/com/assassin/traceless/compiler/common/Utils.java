package com.assassin.traceless.compiler.common;

import com.assassin.traceless.compiler.setup.MethodSimple;

/**
 * Utils
 * Created by Qulit on 2018/5/23.
 */
public class Utils {

    public static String createMethodName(MethodSimple methodSimple) {
        if (methodSimple != null && methodSimple.enclosingElement != null) {
            String clazzName = methodSimple.enclosingElement.getSimpleName().toString();
            return clazzName + "_" + methodSimple.methodName;
        } else {
            return "";
        }
    }
}
