package com.assassin.traceless.compiler.setup;

import com.assassin.traceless.compiler.common.Constants;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * BuryingSchemaSet
 * Created by Le-q on 2018/3/19.
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
        String clazzName = Constants.BURYING_PREFIX + classNameKey;
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
        return Constants.BURYING_PACKAGE_NAME;
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
        String methodName = methodSimple.methodName;
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .returns(methodSimple.methodReturns)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

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
