package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 * MethodSimple
 * Created by Qulit on 2018/3/20.
 */

public class MethodSimple implements MemberSimple {

    public Element enclosingElement;
    private ExecutableElement executableElement;
    TypeName methodReturns;
    public String methodName;
    List<? extends VariableElement> methodParameters;

    @Override
    final public void binding(Element element) {
        this.executableElement = (ExecutableElement) element;
        this.enclosingElement = executableElement.getEnclosingElement();
        this.methodReturns = ClassName.get(executableElement.getReturnType());
        this.methodName = executableElement.getSimpleName().toString();
        this.methodParameters = executableElement.getParameters();
    }

    String methodNameMaker() {
        StringBuilder name = new StringBuilder();
        name.append(executableElement.getReturnType().toString()).append(" ");     //函数返回类型
        name.append(enclosingElement.toString()); //类名(包路径)
        name.append(".").append(methodName);//函数名称
        name.append("(");
        List<? extends VariableElement> list = executableElement.getParameters();
        if (list != null && list.size() > 0) {
            Iterator<? extends VariableElement> iterator = list.iterator();
            while (iterator.hasNext()) {
                name.append(iterator.next().asType());
                if (iterator.hasNext()) {
                    name.append(",");
                }
            }
        }
        name.append(")");
        return name.toString();
    }

}
