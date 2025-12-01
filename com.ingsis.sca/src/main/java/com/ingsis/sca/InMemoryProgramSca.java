/*
 * My Project
 */

package com.ingsis.sca;

import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;

public class InMemoryProgramSca implements ProgramSca {
  private final PeekableIterator<Interpretable> checkableStream;
  private final Checker checker;

  public InMemoryProgramSca(
      PeekableIterator<Interpretable> checkableStream, Checker checker) {
    this.checkableStream = checkableStream;
    this.checker = checker;
  }

  @Override
  public Result<String> analyze() {
    while (checkableStream.hasNext()) {
      Checkable next = (Checkable) checkableStream.next();
      Result<String> result = next.acceptChecker(checker);

      if (!result.isCorrect()) {
        return result;
      }
    }
    return new CorrectResult<>("Check passed.");
  }
}
