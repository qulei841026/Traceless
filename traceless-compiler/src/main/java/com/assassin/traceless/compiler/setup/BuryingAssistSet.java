package com.assassin.traceless.compiler.setup;

import com.assassin.traceless.compiler.common.Constants;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * BuryingAssistSet
 * Created by Le-q on 2018/3/21.
 */
public class BuryingAssistSet extends BorrowProcessingEnv implements ISet, ICreator {

    private Partner<MethodSimple> partner;
    private String classNameKey;

    public BuryingAssistSet(ProcessingEnvironment processingEnv) {
        super(processingEnv);
        partner = new Partner<>(this, MethodSimple.class);
    }

    @Override
    public TypeSpec.Builder createClazz() {
        String clazzName = Constants.BURYING_PREFIX + classNameKey;
        TypeSpec.Builder clazz = TypeSpec.classBuilder(clazzName)
                .addSuperinterface(Constants.BURYING_TRANSFER)
                .addModifiers(Modifier.FINAL)
                .addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder methodProcess = createMethodByProcess();
        clazz.addMethod(methodProcess.build());
        return clazz;
    }

    private MethodSpec.Builder createMethodByProcess() {

        MethodSpec.Builder method = MethodSpec.methodBuilder("process")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.OBJECT, "target", Modifier.FINAL)
                .addParameter(String.class, "invokeMethod", Modifier.FINAL)
                .addParameter(Object[].class, "args", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "schema", Modifier.FINAL);

        ClassName schema = ClassName.get(Constants.BURYING_PACKAGE_NAME, "BuryingMasterSchema");
        method.addStatement("$T impl = ($L) schema", schema, "BuryingMasterSchema");

        CodeBlock.Builder caseBlock = CodeBlock.builder().beginControlFlow("switch($L)", "invokeMethod");
        caseBlock.endControlFlow();
        method.addCode(caseBlock.build());

        return method;
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

    public BuryingAssistSet setClassNameKey(String classNameKey) {
        this.classNameKey = classNameKey;
        return this;
    }

}
