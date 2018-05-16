package com.assassin.traceless.compiler;

import com.assassin.traceless.annotations.weaving.Burying;
import com.assassin.traceless.annotations.weaving.Using;
import com.assassin.traceless.compiler.common.Constants;
import com.assassin.traceless.compiler.internal.BorrowProcessingEnv;
import com.assassin.traceless.compiler.setup.BuryingAssistSet;
import com.assassin.traceless.compiler.setup.BuryingSchemaSet;
import com.assassin.traceless.compiler.setup.ISet;
import com.assassin.traceless.compiler.setup.UsingSet;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
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
        return ImmutableSet.of(Burying.class, Using.class);
    }

    @Override
    public Set<? extends Element> process(SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        info("TracelessGenerator process");

        HashMap<String, ISet> mapSet = new HashMap<>();
        for (Element item : elementsByAnnotation.get(Using.class)) {
            String key = item.getEnclosingElement().toString();
            ISet usingSet;
            if (mapSet.containsKey(key)) {
                usingSet = mapSet.get(key);
            } else {
                usingSet = new UsingSet(processingEnv);
                mapSet.put(key, usingSet);
            }
            usingSet.add(item);
        }

        for (Element item : elementsByAnnotation.get(Burying.class)) {
            Burying burying = item.getAnnotation(Burying.class);
            String key = burying.name();

            ISet set;
            String schemaKey = key + Constants.SCHEMA_SUFFIX;
            if (mapSet.containsKey(schemaKey)) {
                set = mapSet.get(schemaKey);
            } else {
                set = new BuryingSchemaSet(processingEnv).setClassNameKey(schemaKey);
                mapSet.put(schemaKey, set);
            }
            set.add(item);

            String assistKey = key + Constants.ASSIST_SUFFIX;
            if (mapSet.containsKey(assistKey)) {
                set = mapSet.get(assistKey);
            } else {
                set = new BuryingAssistSet(processingEnv).setClassNameKey(assistKey);
                mapSet.put(assistKey, set);
            }
            set.add(item);
        }

        try {
            for (Map.Entry<String, ISet> entry : mapSet.entrySet()) {
                JavaFile file = entry.getValue().toFile();
                file.writeTo(filer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ImmutableSet.of();
    }

}