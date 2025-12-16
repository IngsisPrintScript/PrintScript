/*
 * My Project
 */

package com.ingsis.interpreter.visitor.factory;

import com.ingsis.utils.nodes.visitors.Interpreter;

public interface InterpreterVisitorFactory {
    Interpreter createDefaultInterpreter();
}
