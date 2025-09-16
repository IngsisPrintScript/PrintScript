/*
 * My Project
 */

package com.ingsis.printscript.runtime.functions;

import com.ingsis.printscript.visitor.InterpretVisitorInterface;
import java.util.List;

public interface PSFunction {
    Object call(List<Object> args, InterpretVisitorInterface interpreter);

    Class<?> returnType();

    List<Class<?>> parameterTypes();
}
