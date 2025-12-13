/*
 * My Project
 */

package com.ingsis.interpreter.factory;

import com.ingsis.interpreter.LoggerProgramInterpreter;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.runtime.Runtime;
import java.io.InputStream;

public final class InterpreterFactory implements SafeIteratorFactory<String> {
    private final SafeIteratorFactory<Interpretable> interpretableIteratorFactory;
    private final Interpreter interpreter;
    private final IterationResultFactory iterationResultFactory;

    public InterpreterFactory(
            SafeIteratorFactory<Interpretable> interpretableIteratorFactory,
            InterpreterVisitorFactory interpreterFactory,
            Runtime runtime,
            IterationResultFactory iterationResultFactory) {
        this.interpretableIteratorFactory = interpretableIteratorFactory;
        this.interpreter = interpreterFactory.createDefaultInterpreter(runtime);
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterator<String> fromInputStream(InputStream in) {
        return new ProgramInterpreter(
                interpretableIteratorFactory.fromInputStream(in),
                interpreter,
                iterationResultFactory);
    }

    @Override
    public SafeIterator<String> fromInputStreamLogger(InputStream in, String debugPath) {
        try {
            return new LoggerProgramInterpreter(
                    new ProgramInterpreter(
                            interpretableIteratorFactory.fromInputStreamLogger(in, debugPath),
                            interpreter,
                            iterationResultFactory),
                    debugPath,
                    iterationResultFactory);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
