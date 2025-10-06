/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultProgramInterpreter implements ProgramInterpreter {
    private final PeekableIterator<Interpretable> interpretableStream;
    private final Interpreter interpreter;
    private final Runtime runtime;

    public DefaultProgramInterpreter(
            PeekableIterator<Interpretable> interpretableStream,
            Interpreter interpreter,
            Runtime runtime) {
        this.interpretableStream = interpretableStream;
        this.interpreter = interpreter;
        this.runtime = runtime;
    }

    @Override
    public Result<String> interpret() {
        Result<String> interpretResult =
                new IncorrectResult<>("That's not a valid PrintScript expression!");
        runtime.push();
        while (interpretableStream.hasNext()) {
            Interpretable interpretable = interpretableStream.next();
            interpretResult = interpretable.acceptInterpreter(interpreter);
            if (!interpretResult.isCorrect()) {
                return interpretResult;
            }
        }
        runtime.pop();
        return interpretResult;
    }
}
