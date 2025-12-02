/*
 * My Project
 */

package com.ingsis.interpreter.factory;

import com.ingsis.interpreter.DefaultProgramInterpreter;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.peekableiterator.factories.PeekableIteratorFactory;
import java.io.InputStream;

public final class DefaultProgramInterpreterFactory implements ProgramInterpreterFactory {
    private final PeekableIteratorFactory<Interpretable> checkableIteratorFactory;
    private final Interpreter interpreter;

    public DefaultProgramInterpreterFactory(
            PeekableIteratorFactory<Interpretable> checkableIteratorFactory,
            InterpreterVisitorFactory interpreterFactory,
            Runtime runtime) {
        this.checkableIteratorFactory = checkableIteratorFactory;
        this.interpreter = interpreterFactory.createDefaultInterpreter(runtime);
    }

    @Override
    public ProgramInterpreter fromInputStream(InputStream in) {
        return new DefaultProgramInterpreter(
                checkableIteratorFactory.fromInputStream(in), interpreter);
    }
}
