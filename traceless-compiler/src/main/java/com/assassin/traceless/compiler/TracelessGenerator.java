package com.assassin.traceless.compiler;

import com.assassin.traceless.annotations.compiler.Reporter;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

/**
 * TracelessGenerator
 * Created by Qulit on 2018/2/2.
 */

public class TracelessGenerator implements BasicAnnotationProcessor.ProcessingStep {

//    private static final ClassName JoinPoint = ClassName.get("org.aspectj.lang", "JoinPoint");

    private ProcessingEnvironment processingEnv;

    TracelessGenerator(ProcessingEnvironment env) {
        this.processingEnv = env;
    }

    private void info(String msg, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        info("TracelessGenerator annotations");
        return ImmutableSet.of(Reporter.class);
    }

    @Override
    public Set<? extends Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        info("TracelessGenerator process");

        for (Object o : elementsByAnnotation.values()) {
            info("elements=%s", o + "");
        }

        info("TracelessGenerator process : " + elementsByAnnotation.values());
        for (ExecutableElement method : ElementFilter.methodsIn(elementsByAnnotation.values())) {
            info("method=%s", method + "");
            ClassName logs = ClassName.get("android.util", "Log");
            TypeSpec.Builder tBuilder = TypeSpec.classBuilder("Reporter$$Traceless")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Aspect.class);
            MethodSpec.Builder mBuilder = MethodSpec.methodBuilder("bindView")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(JoinPoint.class, "joinPoint");
            CodeBlock.Builder toStringCodeBuilder = CodeBlock.builder();
            toStringCodeBuilder.add("$T.d(\"qulei\",\"Before\");", logs);
            mBuilder.addCode(toStringCodeBuilder.build());
            AnnotationSpec.Builder aBuilder = AnnotationSpec.builder(Before.class);
            CodeBlock.Builder codeBlockBuilder = CodeBlock.builder().add("$S", "execution(* android.app.Activity.on**(..))");
            aBuilder.addMember("value", codeBlockBuilder.build());
            mBuilder.addAnnotation(aBuilder.build());
            tBuilder.addMethod(mBuilder.build());
            JavaFile jf = JavaFile.builder("com.assassin.traceless.test", tBuilder.build())
                    .build();
            try {
                jf.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                info("IOException : %s", e.getMessage());
            }
        }
        return ImmutableSet.of();
    }
}