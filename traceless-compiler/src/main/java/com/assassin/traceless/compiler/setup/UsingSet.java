package com.assassin.traceless.compiler.setup;

import com.assassin.traceless.annotations.weaving.Using;
import com.assassin.traceless.compiler.common.Constants;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * UsingSet
 * Created by Qulit 2018/2/28.
 */
public class UsingSet extends BorrowProcessingEnv {

    private static final String SUFFIX = "$$FieldAspect";

    private Element element;
    private TypeElement typeElement;
    private VariableElement variableElement;

    private String asType;
    private String clazzFullName;
    private String clazzName;
    private String fieldName;

    public UsingSet(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public void add(Element element) {
        this.element = element;
        this.typeElement = (TypeElement) element.getEnclosingElement();
        this.variableElement = (VariableElement) element;
        this.clazzName = typeElement.getSimpleName().toString();
        this.fieldName = variableElement.getSimpleName().toString();
        this.asType = variableElement.asType().toString();
        this.clazzFullName = typeElement.toString();
    }

    public JavaFile toFile() {
        String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
        return JavaFile.builder(packageName, createClazz().build()).build();
    }

    private TypeSpec.Builder createClazz() {
        TypeSpec.Builder clazz = createTypeSpec(typeElement);
        clazz.addMethod(createMethodByGet().build());
        clazz.addMethod(createMethodBySet().build());
        return clazz;
    }

    private TypeSpec.Builder createTypeSpec(TypeElement typeElement) {
        return TypeSpec.classBuilder(typeElement.getSimpleName() + SUFFIX)
                .addAnnotation(Aspect.class)
                .addModifiers(Modifier.PUBLIC);
    }

    private MethodSpec.Builder createMethodByGet() {
        String methodName = "get_field_" + fieldName + "_" + clazzName;
        MethodSpec.Builder method = createMethod(methodName);

        CodeBlock.Builder methodCoder = createMethodBody();
        method.addCode(methodCoder.build());

        AnnotationSpec.Builder annotation = createMethodAnnotation("get");
        method.addAnnotation(annotation.build());
        return method;
    }

    private MethodSpec.Builder createMethodBySet() {
        String methodName = "set_field_" + fieldName + "_" + clazzName;
        MethodSpec.Builder method = createMethod(methodName);

        CodeBlock.Builder methodCoder = createMethodBody();
        method.addCode(methodCoder.build());

        AnnotationSpec.Builder annotation = createMethodAnnotation("set");
        method.addAnnotation(annotation.build());

        return method;
    }

    private MethodSpec.Builder createMethod(String methodName) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(Object.class)
                .addException(Throwable.class)
                .addParameter(ProceedingJoinPoint.class, "joinPoint");
    }

    private AnnotationSpec.Builder createMethodAnnotation(String way) {
        CodeBlock.Builder annotationCoder = CodeBlock.builder()
                .add("$S", way + "(" + asType + " " + clazzFullName + "." + fieldName + ")");
        return AnnotationSpec.builder(Around.class)
                .addMember("value", annotationCoder.build());
    }

    private CodeBlock.Builder createMethodBody() {
        Using using = variableElement.getAnnotation(Using.class);
        String tag = using.tag();
        return CodeBlock.builder()
                .add("return $T.process(\"" + tag + "\",joinPoint);", Constants.USING_TRANSFER);
    }

}
