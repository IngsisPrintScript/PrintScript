/*
 * My Project
 */

package com.ingsis.sca;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;

public class ProgramSca implements SafeIterator<String> {
  private final SafeIterator<Interpretable> checkableStream;
  private final Checker checker;
  private final SemanticEnvironment env;
  private final IterationResultFactory iterationResultFactory;

  public ProgramSca(
      SafeIterator<Interpretable> checkableStream,
      Checker checker,
      SemanticEnvironment env,
      IterationResultFactory iterationResultFactory) {
    this.checkableStream = checkableStream;
    this.checker = checker;
    this.env = env;
    this.iterationResultFactory = iterationResultFactory;
  }

  @Override
  public SafeIterationResult<String> next() {
    SafeIterationResult<Interpretable> result = checkableStream.next();
    if (!result.isCorrect()) {
      return iterationResultFactory.createIncorrectResult(result.error());
    }
    Checkable checkable = (Checkable) result.iterationResult();
    return switch (checkable.acceptChecker(checker, env)) {
      case CheckResult.INCORRECT I -> iterationResultFactory.createIncorrectResult(I.error());
      case CheckResult.CORRECT C -> buildCorrectResult(result, C);
    };
  }

  private SafeIterationResult<String> buildCorrectResult(
      SafeIterationResult<Interpretable> checkableStream, CheckResult.CORRECT result) {
    return iterationResultFactory.createCorrectResult(
        "Checks passed",
        new ProgramSca(
            checkableStream.nextIterator(),
            this.checker,
            result.environment(),
            this.iterationResultFactory));
  }
}
