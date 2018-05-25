package com.assassin.traceless.compiler.setup;

import com.assassin.traceless.compiler.common.Utils;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.assassin.traceless.rules.ClazzRule;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.ref.WeakReference;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * BuryingSchemaSet
 * Created by Qulit on 2018/3/19.
 */
public class BuryingSchemaSet extends BorrowProcessingEnv implements ISet, ICreator {

    private Partner<MethodSimple> partner;
    private String classNameKey;

    public BuryingSchemaSet(ProcessingEnvironment processingEnv) {
        super(processingEnv);
        partner = new Partner<>(this, MethodSimple.class);
    }

    @Override
    public TypeSpec.Builder createClazz() {
        String clazzName = ClazzRule.BURYING_PREFIX + classNameKey;
        TypeSpec.Builder clazz = TypeSpec.interfaceBuilder(clazzName);
        clazz.addModifiers(Modifier.PUBLIC);

        for (MethodSimple item : partner.listMember) {
            MethodSpec.Builder method = createMethod(item);
            clazz.addMethod(method.build());
        }

        return clazz;
    }

    @Override
    public String createPackage() {
        return ClazzRule.BURYING_PACKAGE_NAME;
    }

    @Override
    public void add(Element element) {
        partner.add(element);
    }

    @Override
    public JavaFile toFile() {
        return partner.toFile();
    }

    private MethodSpec.Builder createMethod(MethodSimple methodSimple) {
        String methodName = Utils.createMethodName(methodSimple);
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .returns(methodSimple.methodReturns)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        ParameterizedTypeName targetParam =
                ParameterizedTypeName.get(ClassName.get(WeakReference.class),
                        ClassName.get((TypeElement) methodSimple.enclosingElement));
        method.addParameter(targetParam, "target");

        FieldSimple simple = new FieldSimple();
        for (VariableElement item : methodSimple.methodParameters) {
            simple.binding(item);
            method.addParameter(simple.asType, simple.fieldName);
        }
        return method;
    }

    public BuryingSchemaSet setClassNameKey(String classNameKey) {
        this.classNameKey = classNameKey;
        return this;
    }

}
