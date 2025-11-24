/*
 * My Project
 */

package com.ingsis.sca;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Interpretable;

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
