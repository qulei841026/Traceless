package com.assassin.traceless.compiler;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;

import javax.annotation.processing.Processor;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;

/**
 * 注解处理器
 * Created by Qulit on 2018/1/31.
 */
//@AutoService(Processor.class)  // 注册注解处理器的库
public class TracelessCompilerProcessor extends BasicAnnotationProcessor {

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        info("TracelessCompilerProcessor initSteps");
        return ImmutableList.of(new TracelessGenerator(processingEnv));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        info("TracelessCompilerProcessor GetSupportedSourceVersion=%s ", SourceVersion.latestSupported());
        return SourceVersion.latestSupported();
    }

    private void info(String msg, Object... args) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

}



