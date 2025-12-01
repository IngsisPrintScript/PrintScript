/*
 * My Project
 */

package com.ingsis.engine.factories.formatter;

import com.ingsis.engine.factories.semantic.SemanticFactory;
import com.ingsis.formatter.InMemoryProgramFormatter;
import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.formatter.handlers.factories.InMemoryFormatterHandlerFactory;
import com.ingsis.formatter.publishers.factories.InMemoryFormatterPublisherFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.result.factory.LoggerResultFactory;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class InMemoryFormatterFactory implements FormatterFactory {
  private final SemanticFactory semanticFactory;

  public InMemoryFormatterFactory(SemanticFactory semanticFactory) {
    this.semanticFactory = semanticFactory;
  }

  @Override
  public ProgramFormatter fromFile(
      InputStream inputStream,
      Runtime runtime,
      RuleStatusProvider ruleStatusProvider) {
    ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), runtime);
    AtomicReference<Checker> checkerRef = new AtomicReference<>();
    Supplier<Checker> checkerSupplier = checkerRef::get;
    HandlerFactory handlerFactory = new InMemoryFormatterHandlerFactory(
        resultFactory,
        ruleStatusProvider,
        checkerSupplier);
    PublishersFactory publishersFactory = new InMemoryFormatterPublisherFactory(handlerFactory);
    Checker eventsChecker = new EventsChecker(publishersFactory);
    checkerRef.set(eventsChecker);
    return new InMemoryProgramFormatter(semanticFactory.fromInputStream(inputStream), eventsChecker);
  }
}
