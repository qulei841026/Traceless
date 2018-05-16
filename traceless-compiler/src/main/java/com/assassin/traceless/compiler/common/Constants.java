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


    public static final String BURYING_PACKAGE_NAME = "com.assassin.traceless.burying.build";

    public static final String BURYING_PREFIX = "Burying";
    public static final String SCHEMA_SUFFIX = "Schema";
    public static final String ASSIST_SUFFIX = "Assist";


}
