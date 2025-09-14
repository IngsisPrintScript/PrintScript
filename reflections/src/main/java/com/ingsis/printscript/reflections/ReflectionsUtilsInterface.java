package com.ingsis.printscript.reflections;

import java.util.Collection;

public interface ReflectionsUtilsInterface {

    interface SubclassQuery<T> {
        SubclassQuery<T> inPackages(Collection<String> packages);
        SubclassQuery<T> includeAbstract(boolean include);
        SubclassQuery<T> withAnnotation(Class<?> annotationClass);
        Collection<Class<? extends T>> find();
    }

    <T> SubclassQuery<T> findSubclassesOf(Class<T> baseClass);
    <T> SubclassQuery<T> findImplementationsOf(Class<T> interfaceClass);
}
