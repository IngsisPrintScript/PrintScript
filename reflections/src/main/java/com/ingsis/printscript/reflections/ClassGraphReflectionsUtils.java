/*
 * My Project
 */

package com.ingsis.printscript.reflections;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClassGraphReflectionsUtils implements ReflectionsUtilsInterface {

    @Override
    public <T> SubclassQuery<T> findSubclassesOf(Class<T> baseClass) {
        return new ClassGraphSubclassQuery<>(baseClass, false);
    }

    @Override
    public <T> SubclassQuery<T> findImplementationsOf(Class<T> interfaceClass) {
        return new ClassGraphSubclassQuery<>(interfaceClass, true);
    }

    private static class ClassGraphSubclassQuery<T> implements SubclassQuery<T> {
        private final Class<T> targetClass;
        private final boolean isInterfaceQuery;
        private Collection<String> packages = Collections.emptyList();
        private boolean includeAbstract = false;
        private Class<?> annotationClass = null;

        public ClassGraphSubclassQuery(Class<T> targetClass, boolean isInterfaceQuery) {
            this.targetClass = Objects.requireNonNull(targetClass, "Target class cannot be null");
            this.isInterfaceQuery = isInterfaceQuery;
        }

        @Override
        public SubclassQuery<T> inPackages(Collection<String> packages) {
            this.packages =
                    packages != null
                            ? new ArrayList<>(packages)
                            : // Defensive copy
                            Collections.emptyList();
            return this;
        }

        @Override
        public SubclassQuery<T> includeAbstract(boolean include) {
            this.includeAbstract = include;
            return this;
        }

        @Override
        public SubclassQuery<T> withAnnotation(Class<?> annotationClass) {
            this.annotationClass = annotationClass;
            return this;
        }

        @Override
        public Collection<Class<? extends T>> find() {
            ClassGraph classGraph = new ClassGraph().enableClassInfo().ignoreClassVisibility();

            if (annotationClass != null) {
                classGraph.enableAnnotationInfo();
            }

            if (!packages.isEmpty()) {
                classGraph.acceptPackages(packages.toArray(new String[0]));
            }

            try (ScanResult scanResult = classGraph.scan()) {
                var classInfoList =
                        isInterfaceQuery
                                ? scanResult.getClassesImplementing(targetClass.getName())
                                : scanResult.getSubclasses(targetClass.getName());

                // ✅ Keep only *immediate* subclasses (direct children)
                classInfoList = classInfoList.filter(
                        classInfo ->
                                classInfo.getSuperclass() != null &&
                                        classInfo.getSuperclass().getName().equals(targetClass.getName())
                );

                if (!includeAbstract) {
                    classInfoList = classInfoList.filter(classInfo -> !classInfo.isAbstract());
                }

                if (annotationClass != null) {
                    classInfoList =
                            classInfoList.filter(
                                    classInfo ->
                                            classInfo.hasAnnotation(annotationClass.getName()));
                }

                Collection<Class<?>> loadedClasses = classInfoList.loadClasses();
                List<Class<? extends T>> result = new ArrayList<>();

                for (Class<?> clazz : loadedClasses) {
                    try {
                        result.add(clazz.asSubclass(targetClass));
                    } catch (ClassCastException e) {
                        throw new IllegalStateException(
                                "Class "
                                        + clazz.getName()
                                        + " is not a subclass of "
                                        + targetClass.getName(),
                                e);
                    }
                }

                return result;
            } catch (Exception e) {
                throw new ReflectionException("Failed to scan classes", e);
            }
        }
    }

    public static class ReflectionException extends RuntimeException {
        public ReflectionException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReflectionException(String message) {
            super(message);
        }
    }
}
