/*
 * My Project
 */

package com.ingsis.sca;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.rule.observer.EventsChecker;

public class InMemoryProgramSca implements ProgramSca {
    private final PeekableIterator<Interpretable> checkableStream;
    private final EventsChecker eventsChecker;

    public InMemoryProgramSca(
            PeekableIterator<Interpretable> checkableStream, EventsChecker eventsChecker) {
        this.checkableStream = checkableStream;
        this.eventsChecker = eventsChecker;
    }

    @Override
    public Result<String> analyze() {
        while (checkableStream.hasNext()) {
            Checkable next = (Checkable) checkableStream.next();
            Result<String> result = next.acceptChecker(eventsChecker);

            if (!result.isCorrect()) {
                return result;
            }
        }
        return new CorrectResult<>("Check passed.");
    }
}
