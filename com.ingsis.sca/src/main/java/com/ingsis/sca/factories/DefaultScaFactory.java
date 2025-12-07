/*
 * My Project
 */

package com.ingsis.sca.factories;

import com.ingsis.sca.InMemoryProgramSca;
import com.ingsis.sca.ProgramSca;
import com.ingsis.sca.observer.handlers.factories.DefaultStaticCodeAnalyzerHandlerFactory;
import com.ingsis.sca.observer.publishers.factories.DefaultStaticCodeAnalyzerPublisherFactory;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.factories.DefaultCheckerFactory;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.runtime.result.factory.LoggerResultFactory;
import com.ingsis.utils.runtime.Runtime;

import java.io.InputStream;

public class DefaultScaFactory implements ScaFactory {
  private final SafeIteratorFactory<Interpretable> checkablePeekableIteratorFactory;

  public DefaultScaFactory(
      SafeIteratorFactory<Interpretable> checkablePeekableIteratorFactory) {
    this.checkablePeekableIteratorFactory = checkablePeekableIteratorFactory;
  }

  @Override
  public ProgramSca fromFile(
      InputStream in, RuleStatusProvider ruleStatusProvider, Runtime runtime) {
    ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), runtime);
    HandlerFactory handlerFactory = new DefaultStaticCodeAnalyzerHandlerFactory(resultFactory, ruleStatusProvider);
    PublishersFactory publishersFactory = new DefaultStaticCodeAnalyzerPublisherFactory(handlerFactory);
    Checker eventsChecker = new DefaultCheckerFactory().createInMemoryEventBasedChecker(publishersFactory);
    return new InMemoryProgramSca(
        checkablePeekableIteratorFactory.fromInputStream(in), eventsChecker);
  }
}
