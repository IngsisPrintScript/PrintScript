/*
 * My Project
 */

package com.ingsis.sca.factories;

import com.ingsis.sca.ProgramSca;
import com.ingsis.sca.observer.handlers.factories.DefaultStaticCodeAnalyzerHandlerFactory;
import com.ingsis.utils.evalstate.env.semantic.factories.DefaultSemanticEnvironmentFactory;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.rule.observer.factories.DefaultCheckerFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import java.io.InputStream;

public class ScaFactory implements SafeIteratorFactory<String> {
  private final SafeIteratorFactory<Interpretable> checkableIteratorFactory;
  private final IterationResultFactory iterationResultFactory;
  private final RuleStatusProvider ruleStatusProvider;

  public ScaFactory(
      SafeIteratorFactory<Interpretable> checkableIteratorFactory,
      IterationResultFactory iterationResultFactory,
      RuleStatusProvider ruleStatusProvider) {
    this.checkableIteratorFactory = checkableIteratorFactory;
    this.iterationResultFactory = iterationResultFactory;
    this.ruleStatusProvider = ruleStatusProvider;
  }

  @Override
  public SafeIterator<String> fromInputStream(InputStream in) {
    Checker eventsChecker = new DefaultCheckerFactory()
        .createInMemoryEventBasedChecker(
            new DefaultStaticCodeAnalyzerHandlerFactory(ruleStatusProvider));
    return new ProgramSca(
        checkableIteratorFactory.fromInputStream(in),
        eventsChecker,
        new DefaultSemanticEnvironmentFactory().root(),
        iterationResultFactory);
  }

  @Override
  public SafeIterator<String> fromInputStreamLogger(InputStream in, String debugPath) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fromInputStreamLogger'");
  }
}
