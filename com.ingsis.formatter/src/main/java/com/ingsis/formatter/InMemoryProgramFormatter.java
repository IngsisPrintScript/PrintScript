package com.ingsis.formatter;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.rule.observer.EventsChecker;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Interpretable;

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
    Result<String> finalResult = new IncorrectResult<>("");
    while (checkableStream.hasNext()) {
      Checkable next = (Checkable) checkableStream.next();
      finalResult = next.acceptChecker(eventsChecker);
      if (!finalResult.isCorrect()) {
        return finalResult;
      } else {
        System.out.println(finalResult.result());
      }
    }
    return new CorrectResult<String>("Check passed.");
  }

}
