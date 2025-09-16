/*
 * My Project
 */

package com.ingsis.printscript.runtime.functions;

import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import java.util.List;
import java.util.function.Function;

public final class BuiltInFunction implements PSFunction {
    private final List<Class<?>> parameterTypes;
    private final Class<?> returnType;
    private final Function<List<Object>, Object> implementation;

    public BuiltInFunction(
            List<Class<?>> parameterTypes,
            Class<?> returnType,
            Function<List<Object>, Object> implementation) {
        this.parameterTypes = List.copyOf(parameterTypes);
        this.returnType = returnType;
        this.implementation = implementation;
    }

    @Override
    public Object call(List<Object> args) {
        return implementation.apply(args);
    }

    public Class<?> returnType() {
        return returnType;
    }

    @Override
    public List<Class<?>> parameterTypes() {
        return List.copyOf(parameterTypes);
    }
}
