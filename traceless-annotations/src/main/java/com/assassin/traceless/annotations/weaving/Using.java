package com.assassin.traceless.annotations.weaving;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using(全局变量使用日志)
 * Created by Qulit on 2018/2/26.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Using {
    String tag() default "Using";
}
