/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;

public final class DefaultProgramInterpreter implements ProgramInterpreter {
    private final PeekableIterator<Interpretable> interpretableStream;
    private final Interpreter interpreter;

    public DefaultProgramInterpreter(
            PeekableIterator<Interpretable> interpretableStream, Interpreter interpreter) {
        this.interpretableStream = interpretableStream;
        this.interpreter = interpreter;
    }

    @Override
    public Result<String> interpret() {
        while (interpretableStream.hasNext()) {
            Interpretable interpretable = interpretableStream.next();
            Result<String> interpretResult = interpretable.acceptInterpreter(interpreter);
            if (!interpretResult.isCorrect()) {
                return interpretResult;
            }
        }
        return new CorrectResult<>("Program interpreted successfully.");
    }
}
