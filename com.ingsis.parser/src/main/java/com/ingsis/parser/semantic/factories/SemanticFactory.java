/*
 * My Project
 */

package com.ingsis.parser.semantic.factories;

import com.ingsis.parser.semantic.LoggerSemanticChecker;
import com.ingsis.parser.semantic.SemanticChecker;
import com.ingsis.parser.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.utils.evalstate.env.semantic.factories.DefaultSemanticEnvironmentFactory;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.rule.observer.factories.DefaultCheckerFactory;
import java.io.InputStream;

public final class SemanticFactory implements SafeIteratorFactory<Interpretable> {
  private final SafeIteratorFactory<Checkable> checkableIteratorFactory;
  private final Checker checker;
  private final IterationResultFactory iterationResultFactory;

  public SemanticFactory(
      SafeIteratorFactory<Checkable> nodeIteratorFactory,
      IterationResultFactory iterationResultFactory) {
    this.checkableIteratorFactory = nodeIteratorFactory;
    this.checker = new DefaultCheckerFactory()
        .createInMemoryEventBasedChecker(new DefaultHandlersFactory());
    this.iterationResultFactory = iterationResultFactory;
  }

  @Override
  public SafeIterator<Interpretable> fromInputStream(InputStream in) {
    return new SemanticChecker(
        checkableIteratorFactory.fromInputStream(in),
        checker,
        new DefaultSemanticEnvironmentFactory().root(),
        iterationResultFactory);
  }

  @Override
  public SafeIterator<Interpretable> fromInputStreamLogger(InputStream in, String debugPath) {
    try {
      return new LoggerSemanticChecker(
          new SemanticChecker(
              checkableIteratorFactory.fromInputStream(in),
              checker,
              new DefaultSemanticEnvironmentFactory().root(),
              iterationResultFactory),
          debugPath,
          iterationResultFactory);
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
