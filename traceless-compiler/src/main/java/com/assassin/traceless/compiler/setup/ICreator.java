package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.TypeSpec;

/**
 * Created by Le-q on 2018/3/19.
 */
interface ICreator {

    TypeSpec.Builder createClazz();

    String createPackage();

}
