package com.assassin.traceless.compiler;

import com.assassin.traceless.annotations.weaving.Burying;
import com.assassin.traceless.annotations.weaving.Using;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


//@AutoService(Processor.class)  // 注册注解处理器的库
@SuppressWarnings("WeakerAccess")
public class NormalProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elements;
    private static Messager messager;

    private void error(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    static void info(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();
        elements = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();

        info("LeViewBinderProcessor init", "");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        info("RoundEnvironment : %s .", roundEnv);

        for (Element element : roundEnv.getElementsAnnotatedWith(Using.class)) {
            info("element=%s", element);
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(Burying.class)) {
            info("element=%s", element);
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(Using.class);
        annotations.add(Burying.class);
        return annotations;
    }


}
