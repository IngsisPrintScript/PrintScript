/*
 * My Project
 */

package com.ingsis.interpreter.factories;

import com.ingsis.interpreter.DefaultProgramInterpreter;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;

public final class DefaultInterpreterFactory implements InterpreterFactory {
    @Override
    public ProgramInterpreter createDefaultInterpreter(
            PeekableIterator<Interpretable> peekableIterator, Interpreter interpreter) {
        return new DefaultProgramInterpreter(peekableIterator, interpreter);
    }
}
