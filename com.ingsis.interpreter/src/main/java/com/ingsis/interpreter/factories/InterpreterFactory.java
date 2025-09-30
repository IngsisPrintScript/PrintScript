/*
 * My Project
 */

package com.ingsis.interpreter.factories;

import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;

public interface InterpreterFactory {
    ProgramInterpreter createDefaultInterpreter(
            PeekableIterator<Interpretable> peekableIterator, Interpreter interpreter);
}
