/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;

public class InMemoryProgramFormatter implements ProgramFormatter {
  private final PeekableIterator<Interpretable> checkableStream;
  private final Checker checker;

  public InMemoryProgramFormatter(
      PeekableIterator<Interpretable> checkableStream, Checker eventsChecker) {
    this.checkableStream = checkableStream;
    this.checker = eventsChecker;
  }

  @Override
  public Result<String> format() {
    StringBuilder sb = new StringBuilder();
    Result<String> finalResult;

    while (checkableStream.hasNext()) {
      Checkable next = (Checkable) checkableStream.next();
      finalResult = next.acceptChecker(checker);

      if (!finalResult.isCorrect()) {
        return finalResult;
      }

      sb.append(finalResult.result());
    }

    return new CorrectResult<>(sb.toString());
  }
}
