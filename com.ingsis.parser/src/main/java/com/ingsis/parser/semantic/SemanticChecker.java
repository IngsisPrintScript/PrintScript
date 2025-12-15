/*
 * My Project
 */

package com.ingsis.parser.semantic;

import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class SemanticChecker implements SafeIterator<Interpretable> {
  private final SafeIterator<Checkable> checkableStream;
  private final Checker checker;
  private final SemanticEnvironment env;
  private final IterationResultFactory iterationResultFactory;

  public SemanticChecker(
      SafeIterator<Checkable> checkableStream,
      Checker checker,
      SemanticEnvironment env,
      IterationResultFactory iterationResultFactory) {
    this.checkableStream = checkableStream;
    this.checker = checker;
    this.env = env;
    this.iterationResultFactory = iterationResultFactory;
  }

  private CheckResult parse(Checkable checkable) {
    return checkable.acceptChecker(checker, env);
  }

  @Override
  public SafeIterationResult<Interpretable> next() {
    SafeIterationResult<Checkable> iterationResult = checkableStream.next();
    if (!iterationResult.isCorrect()) {
      return iterationResultFactory.cloneIncorrectResult(iterationResult);
    }
    return switch (parse(iterationResult.iterationResult())) {
      case CheckResult.CORRECT C ->
        createCorrectResult(C, iterationResult.nextIterator(), (Interpretable) iterationResult.iterationResult());
      case CheckResult.INCORRECT I -> iterationResultFactory.createIncorrectResult(I.error());
    };
  }

  private SafeIterationResult<Interpretable> createCorrectResult(CheckResult.CORRECT result,
      SafeIterator<Checkable> nextIterator, Interpretable interpretable) {
    return iterationResultFactory.createCorrectResult(
        interpretable,
        new SemanticChecker(
            nextIterator,
            this.checker,
            result.environment(),
            this.iterationResultFactory));
  }
}
