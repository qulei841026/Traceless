package com.assassin.traceless.compiler.setup;

import com.squareup.javapoet.JavaFile;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Partner
 * Created by Qulit on 2018/3/19.
 */
class Partner<T extends MemberSimple> {

    List<T> listMember;
    TypeElement typeElement;
    String clazzName;
    String clazzFullName;

    private ICreator creator;
    private Class<T> simple;

    Partner(ICreator creator, Class<T> clazz) {
        this.creator = creator;
        this.simple = clazz;
        this.listMember = new ArrayList<>();
    }

    public void add(Element element) {
        this.typeElement = (TypeElement) element.getEnclosingElement();
        this.clazzName = typeElement.getSimpleName().toString();
        this.clazzFullName = typeElement.toString();
        addSimple(element);
    }

    public JavaFile toFile() {
        return JavaFile.builder(creator.createPackage(), creator.createClazz().build()).build();
    }

    private void addSimple(Element element) {
        T item = creator();
        if (item != null) {
            item.binding(element);
            listMember.add(item);
        }
    }

    private T creator() {
        try {
            return simple.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
