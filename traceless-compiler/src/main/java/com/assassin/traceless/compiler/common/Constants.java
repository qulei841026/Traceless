package com.assassin.traceless.compiler.common;

import com.squareup.javapoet.ClassName;

/**
 * Constants
 * Created by Qulit on 2018/3/1.
 */
public class Constants {
    public static final ClassName USING_TRANSFER = ClassName
            .get("com.assassin.traceless.core.external", "UsingTransfer");

    public static final ClassName BURYING_TRANSFER = ClassName
            .get("com.assassin.traceless.core.external", "BuryingTransfer");

    public static final ClassName LOG = ClassName.get("android.util", "Log");


}
