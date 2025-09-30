/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.runtime.Runtime;
import com.ingsis.visitors.Interpreter;

public interface InterpreterVisitorFactory {
    Interpreter createDefaultInterpreter(Runtime runtime);
}
