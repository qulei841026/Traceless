package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Le-q on 2018/3/20.
 */
public class FieldSimple implements MemberSimple {

    VariableElement variableElement;
    TypeName asType;
    String fieldName;
    String fieldType;

    @Override
    final public void binding(Element element) {
        this.variableElement = (VariableElement) element;
        TypeMirror mirror = variableElement.asType();
        this.asType = ClassName.get(mirror);
        this.fieldType = mirror.toString();
        this.fieldName = variableElement.getSimpleName().toString();
    }

}
