/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.runtime.Runtime;

public interface InterpreterVisitorFactory {
    Interpreter createDefaultInterpreter(Runtime runtime);
}
