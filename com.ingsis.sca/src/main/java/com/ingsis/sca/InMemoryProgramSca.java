/*
 * My Project
 */

package com.ingsis.sca;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;

public class InMemoryProgramSca implements ProgramSca {
  private final SafeIterator<Interpretable> checkableStream;
  private final Checker checker;

  public InMemoryProgramSca(SafeIterator<Interpretable> checkableStream, Checker checker) {
    this.checkableStream = checkableStream;
    this.checker = checker;
  }

  @Override
  public Result<String> analyze() {
    SafeIterationResult<Interpretable> result = checkableStream.next();
    while (result.isCorrect()) {
      Interpretable interpretable = result.iterationResult();
      Result<String> checkResult = ((Checkable) interpretable).acceptChecker(checker);
      if (!checkResult.isCorrect()) {
        return checkResult;
      }
      result = result.nextIterator().next();
    }
    return new CorrectResult<String>("All checks passed.");
  }
}
