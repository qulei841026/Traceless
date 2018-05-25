package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.TypeSpec;

/**
 * ICreator
 * Created by Qulit on 2018/3/19.
 */
interface ICreator {

    TypeSpec.Builder createClazz();

    String createPackage();

}
