/*
 * My Project
 */

package com.ingsis.interpreter.factory;

import com.ingsis.interpreter.LoggerProgramInterpreter;
import com.ingsis.interpreter.ProgramInterpreter;
import com.ingsis.interpreter.visitor.factory.InterpreterVisitorFactory;
import com.ingsis.utils.evalstate.factories.EvalStateFactory;
import com.ingsis.utils.evalstate.io.InputSupplier;
import com.ingsis.utils.evalstate.io.OutputEmitter;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import java.io.InputStream;

public final class InterpreterFactory implements SafeIteratorFactory<String> {
    private final SafeIteratorFactory<Interpretable> interpretableIteratorFactory;
    private final Interpreter interpreter;
    private final IterationResultFactory iterationResultFactory;
    private final EvalStateFactory evalStateFactory;
    private final OutputEmitter emitter;
    private final InputSupplier supplier;

    public InterpreterFactory(
            SafeIteratorFactory<Interpretable> interpretableIteratorFactory,
            InterpreterVisitorFactory interpreterFactory,
            OutputEmitter emitter,
            InputSupplier supplier,
            IterationResultFactory iterationResultFactory,
            EvalStateFactory evalStateFactory) {
        this.interpretableIteratorFactory = interpretableIteratorFactory;
        this.interpreter = interpreterFactory.createDefaultInterpreter();
        this.emitter = emitter;
        this.supplier = supplier;
        this.iterationResultFactory = iterationResultFactory;
        this.evalStateFactory = evalStateFactory;
    }

    @Override
    public SafeIterator<String> fromInputStream(InputStream in) {
        return new ProgramInterpreter(
                interpretableIteratorFactory.fromInputStream(in),
                evalStateFactory.create(emitter, supplier),
                interpreter,
                iterationResultFactory);
    }

    @Override
    public SafeIterator<String> fromInputStreamLogger(InputStream in, String debugPath) {
        try {
            return new LoggerProgramInterpreter(
                    new ProgramInterpreter(
                            interpretableIteratorFactory.fromInputStream(in),
                            evalStateFactory.create(emitter, supplier),
                            interpreter,
                            iterationResultFactory),
                    debugPath,
                    iterationResultFactory);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
