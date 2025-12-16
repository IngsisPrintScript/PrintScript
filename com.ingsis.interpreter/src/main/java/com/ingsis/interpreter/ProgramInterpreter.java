/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class ProgramInterpreter implements SafeIterator<String> {
    private final SafeIterator<Interpretable> interpretableStream;
    private final EvalState evalState;
    private final Interpreter interpreter;
    private final IterationResultFactory iterationResultFactory;

    public ProgramInterpreter(
            SafeIterator<Interpretable> interpretableStream,
            EvalState evalState,
            Interpreter interpreter,
            IterationResultFactory iterationResultFactory) {
        this.interpretableStream = interpretableStream;
        this.evalState = evalState;
        this.interpreter = interpreter;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterationResult<String> next() {
        SafeIterationResult<Interpretable> iterationResult = interpretableStream.next();
        if (!iterationResult.isCorrect()) {
            return iterationResultFactory.cloneIncorrectResult(iterationResult);
        }
        InterpretResult interpretResult =
                iterationResult.iterationResult().acceptInterpreter(interpreter, evalState);
        return switch (interpretResult) {
            case InterpretResult.INCORRECT I ->
                    iterationResultFactory.createIncorrectResult(I.error());
            case InterpretResult.CORRECT C ->
                    createCorrectResult(C, iterationResult.nextIterator());
        };
    }

    private SafeIterationResult<String> createCorrectResult(
            InterpretResult.CORRECT result, SafeIterator<Interpretable> nextIterator) {
        return iterationResultFactory.createCorrectResult(
                "Interpreted succesfully",
                new ProgramInterpreter(
                        nextIterator,
                        result.evalState(),
                        this.interpreter,
                        this.iterationResultFactory));
    }
}
