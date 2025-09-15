package com.ingsis.printscript.runtime.functions;

import java.util.List;
import java.util.function.Function;

public class BuiltInFunction {
    private final List<Class<?>> parameterTypes;
    private final Class<?> returnType;
    private final Function<List<Object>, Object> implementation;

    public BuiltInFunction(List<Class<?>> parameterTypes, Class<?> returnType, Function<List<Object>, Object> implementation) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.implementation = implementation;
    }

    public Object call(List<Object> args) {
        return implementation.apply(args);
    }
}
