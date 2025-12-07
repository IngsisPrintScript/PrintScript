/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.io.IOException;
import java.io.Writer;

public class InMemoryProgramFormatter implements ProgramFormatter {
    private final SafeIterator<Interpretable> checkableStream;
    private final Checker checker;
    private final Writer writer;

    public InMemoryProgramFormatter(
            SafeIterator<Interpretable> checkableStream, Checker eventsChecker, Writer writer) {
        this.checkableStream = checkableStream;
        this.checker = eventsChecker;
        this.writer = writer;
    }

    @Override
    public Result<String> format() {
        SafeIterationResult<Interpretable> result = checkableStream.next();
        while (result.isCorrect()) {
            Result<String> finalResult =
                    ((Checkable) result.iterationResult()).acceptChecker(checker);
            if (!finalResult.isCorrect()) {
                return finalResult;
            }
            result = result.nextIterator().next();
            if (result.isCorrect()) {
                try {
                    writer.append("\n");
                } catch (IOException e) {
                    return new IncorrectResult<>(e.getMessage());
                }
            }
        }
        return new CorrectResult<>("Formatted.");
    }
}
