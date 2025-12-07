/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class ProgramInterpreter implements SafeIterator<String> {
    private final SafeIterator<Interpretable> interpretableStream;
    private final Interpreter interpreter;
    private final IterationResultFactory iterationResultFactory;

    public ProgramInterpreter(
            SafeIterator<Interpretable> interpretableStream,
            Interpreter interpreter,
            IterationResultFactory iterationResultFactory) {
        this.interpretableStream = interpretableStream;
        this.interpreter = interpreter;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterationResult<String> next() {
        SafeIterationResult<Interpretable> iterationResult = interpretableStream.next();
        if (!iterationResult.isCorrect()) {
            return iterationResultFactory.cloneIncorrectResult(iterationResult);
        }
        Result<String> interpretResult =
                iterationResult.iterationResult().acceptInterpreter(interpreter);
        if (!interpretResult.isCorrect()) {
            return iterationResultFactory.createIncorrectResult(interpretResult.error());
        }
        return iterationResultFactory.createCorrectResult(
                interpretResult.result(),
                new ProgramInterpreter(
                        iterationResult.nextIterator(),
                        this.interpreter,
                        this.iterationResultFactory));
    }
}
