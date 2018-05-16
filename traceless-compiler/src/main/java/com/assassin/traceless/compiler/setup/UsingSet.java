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

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * UsingSet
 * Created by Qulit 2018/2/28.
 */
public class UsingSet extends BorrowProcessingEnv implements ISet, ICreator {

    private static final String SUFFIX = "$$FieldAspect";

    private Partner<FieldSimple> partner;

    public UsingSet(ProcessingEnvironment processingEnv) {
        super(processingEnv);
        partner = new Partner<>(this, FieldSimple.class);
    }

    @Override
    public void add(Element element) {
        partner.add(element);
    }

    @Override
    public JavaFile toFile() {
        return partner.toFile();
    }

    @Override
    public String createPackage() {
        return elements.getPackageOf(partner.typeElement).getQualifiedName().toString();
    }

    @Override
    public TypeSpec.Builder createClazz() {
        TypeSpec.Builder clazz = createTypeSpec(partner.typeElement);
        List<FieldSimple> listField = partner.listMember;
        for (FieldSimple item : listField) {
            clazz.addMethod(createMethodByGet(item).build());
            clazz.addMethod(createMethodBySet(item).build());
        }
        return clazz;
    }

    private TypeSpec.Builder createTypeSpec(TypeElement typeElement) {
        return TypeSpec.classBuilder(typeElement.getSimpleName() + SUFFIX)
                .addAnnotation(Aspect.class)
                .addModifiers(Modifier.PUBLIC);
    }

    private MethodSpec.Builder createMethodByGet(FieldSimple fieldSimple) {
        String methodName = "get_field_" + fieldSimple.fieldName + "_" + partner.clazzName;
        MethodSpec.Builder method = createMethod(methodName);

        CodeBlock.Builder methodCoder = createMethodBody(fieldSimple);
        method.addCode(methodCoder.build());

        AnnotationSpec.Builder annotation = createMethodAnnotation(fieldSimple, "get");
        method.addAnnotation(annotation.build());
        return method;
    }

    private MethodSpec.Builder createMethodBySet(FieldSimple fieldSimple) {
        String methodName = "set_field_" + fieldSimple.fieldName + "_" + partner.clazzName;
        MethodSpec.Builder method = createMethod(methodName);

        CodeBlock.Builder methodCoder = createMethodBody(fieldSimple);
        method.addCode(methodCoder.build());

        AnnotationSpec.Builder annotation = createMethodAnnotation(fieldSimple, "set");
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

    private AnnotationSpec.Builder createMethodAnnotation(FieldSimple fieldSimple, String way) {
        CodeBlock.Builder annotationCoder = CodeBlock.builder()
                .add("$S", way + "(" + fieldSimple.fieldType + " "
                        + partner.clazzFullName + "." + fieldSimple.fieldName + ")");
        return AnnotationSpec.builder(Around.class)
                .addMember("value", annotationCoder.build());
    }

    private CodeBlock.Builder createMethodBody(FieldSimple fieldSimple) {
        Using using = fieldSimple.variableElement.getAnnotation(Using.class);
        String tag = using.tag();
        return CodeBlock.builder()
                .add("return $T.process(\"" + tag + "\",joinPoint);", Constants.USING_TRANSFER);
    }

}
