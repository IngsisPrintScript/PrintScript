/*
 * My Project
 */

package com.ingsis.parser.semantic;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import com.ingsis.utils.runtime.Runtime;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class SemanticChecker implements SafeIterator<Interpretable> {
  private final SafeIterator<Checkable> checkableStream;
  private final Checker checker;
  private final Runtime runtime;
  private final IterationResultFactory iterationResultFactory;

  public SemanticChecker(
      SafeIterator<Checkable> checkableStream,
      Checker checker,
      Runtime runtime,
      IterationResultFactory iterationResultFactory) {
    this.checkableStream = checkableStream;
    this.checker = checker;
    this.runtime = runtime;
    this.iterationResultFactory = iterationResultFactory;
  }

  private Result<Interpretable> parse(Checkable checkable) {
    runtime.push();
    Result<String> checkResult = checkable.acceptChecker(checker);
    runtime.pop();
    if (!checkResult.isCorrect()) {
      return new IncorrectResult<>(checkResult);
    }
    return new CorrectResult<Interpretable>((Interpretable) checkable);
  }

  @Override
  public SafeIterationResult<Interpretable> next() {
    SafeIterationResult<Checkable> iterationResult = checkableStream.next();
    if (!iterationResult.isCorrect()) {
      return iterationResultFactory.cloneIncorrectResult(iterationResult);
    }
    Result<Interpretable> parseResult = parse(iterationResult.iterationResult());
    if (!parseResult.isCorrect()) {
      return iterationResultFactory.createIncorrectResult(parseResult.error());
    }
    return iterationResultFactory.createCorrectResult(
        parseResult.result(),
        new SemanticChecker(
            iterationResult.nextIterator(),
            this.checker,
            this.runtime,
            this.iterationResultFactory));
  }
}
