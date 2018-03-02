package com.assassin.traceless.compiler.internal;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * BorrowProcessingEnv
 * Created by Qulit on 2018/2/28.
 */
public abstract class BorrowProcessingEnv {

    protected ProcessingEnvironment processingEnv;

    protected Filer filer;
    protected Messager messager;
    protected Elements elements;

    public BorrowProcessingEnv(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;

        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
        this.elements = processingEnv.getElementUtils();
    }

    protected void error(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    protected void info(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

}
