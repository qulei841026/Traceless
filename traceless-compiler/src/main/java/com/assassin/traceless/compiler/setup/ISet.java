package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.JavaFile;

import javax.lang.model.element.Element;

/**
 * Created by Le-q on 2018/3/19.
 */

public interface ISet {

    void add(Element element);

    JavaFile toFile();

}
