package com.assassin.traceless.compiler;

import com.assassin.traceless.annotations.weaving.Using;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.assassin.traceless.compiler.setup.UsingSet;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * TracelessGenerator
 * Created by Qulit on 2018/2/2.
 */
public class TracelessGenerator extends BorrowProcessingEnv implements BasicAnnotationProcessor.ProcessingStep {


    TracelessGenerator(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        info("TracelessGenerator annotations");
        return ImmutableSet.of(Using.class);
    }

    @Override
    public Set<? extends Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        info("TracelessGenerator process");
        try {
            if (elementsByAnnotation.keys().contains(Using.class)) {
                for (Element item : elementsByAnnotation.get(Using.class)) {
                    UsingSet set = new UsingSet(processingEnv);
                    set.add(item);
                    JavaFile file = set.toFile();
                    file.writeTo(filer);
                }
            }
        } catch (IOException e) {

        }
        return ImmutableSet.of();
    }

}