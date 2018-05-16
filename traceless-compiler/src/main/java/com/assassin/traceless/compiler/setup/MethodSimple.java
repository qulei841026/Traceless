package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by Le-q on 2018/3/20.
 */

public class MethodSimple implements MemberSimple {

    ExecutableElement executableElement;
    TypeName methodReturns;
    String methodName;
    List<? extends VariableElement> methodParameters;

    @Override
    final public void binding(Element element) {
        this.executableElement = (ExecutableElement) element;
        this.methodReturns = ClassName.get(executableElement.getReturnType());
        this.methodName = executableElement.getSimpleName().toString();
        this.methodParameters = executableElement.getParameters();
    }
}
