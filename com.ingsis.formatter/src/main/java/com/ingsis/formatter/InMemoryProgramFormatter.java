/*
 * My Project
 */

package com.ingsis.formatter;

import java.io.IOException;
import java.io.Writer;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public class InMemoryProgramFormatter implements ProgramFormatter {
  private final PeekableIterator<Interpretable> checkableStream;
  private final Checker checker;
  private final Writer writer;

  public InMemoryProgramFormatter(
      PeekableIterator<Interpretable> checkableStream, Checker eventsChecker, Writer writer) {
    this.checkableStream = checkableStream;
    this.checker = eventsChecker;
    this.writer = writer;
  }

  @Override
  public Result<String> format() {
    Result<String> finalResult;

    while (checkableStream.hasNext()) {
      Checkable next = (Checkable) checkableStream.next();
      finalResult = next.acceptChecker(checker);

      if (!finalResult.isCorrect()) {
        return finalResult;
      }
      if (checkableStream.hasNext()) {
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
