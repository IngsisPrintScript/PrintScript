/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.EventsChecker;

public class InMemoryProgramFormatter implements ProgramFormatter {
    private final PeekableIterator<Interpretable> checkableStream;
    private final EventsChecker eventsChecker;

    public InMemoryProgramFormatter(
            PeekableIterator<Interpretable> checkableStream, EventsChecker eventsChecker) {
        this.checkableStream = checkableStream;
        this.eventsChecker = eventsChecker;
    }

    @Override
    public Result<String> format() {
        StringBuilder sb = new StringBuilder();
        Result<String> finalResult;

        while (checkableStream.hasNext()) {
            Checkable next = (Checkable) checkableStream.next();
            finalResult = next.acceptChecker(eventsChecker);

            if (!finalResult.isCorrect()) {
                return finalResult;
            }

            sb.append(finalResult.result());
        }

        return new CorrectResult<>(sb.toString());
    }
}
