package com.assassin.traceless.compiler.setup;

import com.assassin.traceless.compiler.common.Constants;
import com.assassin.traceless.compiler.common.Utils;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.assassin.traceless.rules.ClazzRule;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * BuryingAssistSet
 * Created by Qulit on 2018/3/21.
 */
public class BuryingAssistSet extends BorrowProcessingEnv implements ISet, ICreator {

    public static final String TAG = "BuryingAssistSet";

    private Partner<MethodSimple> partner;
    private String classNameKey;

    private ClassName schema;
    private String schemaName;

    public BuryingAssistSet(ProcessingEnvironment processingEnv) {
        super(processingEnv);
        partner = new Partner<>(this, MethodSimple.class);
    }

    @Override
    public TypeSpec.Builder createClazz() {
        String clazzName = ClazzRule.BURYING_PREFIX + classNameKey;
        TypeSpec.Builder clazz = TypeSpec.classBuilder(clazzName)
                .addSuperinterface(Constants.BURYING_TRANSFER)
                .addModifiers(Modifier.FINAL)
                .addModifiers(Modifier.PUBLIC);

        for (MethodSimple simple : partner.listMember) {
            clazz.addMethod(createInvokeMethod(simple).build());
        }

        MethodSpec.Builder methodProcess = createMethodByProcess();
        clazz.addMethod(methodProcess.build());
        return clazz;
    }

    private MethodSpec.Builder createInvokeMethod(MethodSimple methodSimple) {
        String methodName = Utils.createMethodName(methodSimple);
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PRIVATE)
                .addParameter(TypeName.OBJECT, "target", Modifier.FINAL)
                .addParameter(Object[].class, "args", Modifier.FINAL)
                .addParameter(schema, "schema", Modifier.FINAL);

        ClassName tClazzName = ClassName.get((TypeElement) methodSimple.enclosingElement);
        ParameterizedTypeName targetParam =
                ParameterizedTypeName.get(ClassName.get(WeakReference.class), tClazzName);
        method.addStatement("$T t = ($L) target", tClazzName, methodSimple.enclosingElement.getSimpleName());
        method.addStatement("$T reference = new $L(t)", targetParam, targetParam.toString());

        CodeBlock.Builder codeBlock = CodeBlock.builder();
        codeBlock.add("schema.$L", methodName);
        codeBlock.add("(");
        if (methodSimple.methodParameters == null || methodSimple.methodParameters.size() == 0) {
            codeBlock.add("reference");
        } else {
            codeBlock.add("reference, ");
            Iterator<? extends VariableElement> iterator = methodSimple.methodParameters.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                VariableElement element = iterator.next();
                codeBlock.add("($T)args[$L]", element.asType(), index);
                if (iterator.hasNext()) {
                    codeBlock.add(", ");
                }
                index++;
            }
        }
        codeBlock.add(");");
        method.addCode(codeBlock.build());
        return method;
    }

    private MethodSpec.Builder createMethodByProcess() {
        MethodSpec.Builder method = MethodSpec.methodBuilder("process")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.OBJECT, "target", Modifier.FINAL)
                .addParameter(String.class, "invokeMethod", Modifier.FINAL)
                .addParameter(Object[].class, "args", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "schema", Modifier.FINAL);

        method.beginControlFlow("if(schema instanceof $T)", schema);
        method.addStatement("$T impl = ($L) schema", schema, schemaName);
        method.addStatement("$T.d" + "("
                + "\"" + TAG + "\"" + ", "                           //tag
                + "\"invokeMethod = \"+invokeMethod"                 //message
                + ")", Constants.LOG);

        List<MethodSimple> listMethod = partner.listMember;

        for (int i = 0, size = listMethod.size(); i < size; i++) {
            method.addCode(createMethodBody(i, listMethod.get(i)).build());
        }

        method.endControlFlow();
        method.beginControlFlow("else");
        method.addStatement("$T.d(\"AssistSet\", \"" + schemaName + " is ClassCastException.\")", Constants.LOG);
        method.endControlFlow();
        return method;
    }

    private CodeBlock.Builder createMethodBody(int index, MethodSimple methodSimple) {
        String invokeMethod = methodSimple.methodNameMaker();
        info("invokeMethod= %s", invokeMethod);
        CodeBlock.Builder builder = CodeBlock.builder();
        StringBuilder sb = new StringBuilder();
        if (index != 0) {
            sb.append("else ");
        }
        sb.append("if(");
        sb.append("\"").append(invokeMethod).append("\"");
        sb.append(".equals(invokeMethod)");
        sb.append(")");
        builder.beginControlFlow(sb.toString());

        String methodName = Utils.createMethodName(methodSimple);
        builder.addStatement(methodName + "(target, args, impl)");

        builder.endControlFlow();
        return builder;
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

    public BuryingAssistSet setClassNameKey(String classNameKey) {
        this.classNameKey = classNameKey;
        this.schemaName = ClazzRule.BURYING_PREFIX
                + classNameKey.replace(ClazzRule.ASSIST_SUFFIX, ClazzRule.SCHEMA_SUFFIX);
        this.schema = ClassName.get(ClazzRule.BURYING_PACKAGE_NAME, schemaName);
        return this;
    }

}
