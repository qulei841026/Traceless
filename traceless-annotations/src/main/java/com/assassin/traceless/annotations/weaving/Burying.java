package com.assassin.traceless.annotations.weaving;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Burying(埋点)
 * Created by Qulit on 2018/2/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Burying {
    Class<?> clazz() default Object.class;
    String name() default "Master";
}
