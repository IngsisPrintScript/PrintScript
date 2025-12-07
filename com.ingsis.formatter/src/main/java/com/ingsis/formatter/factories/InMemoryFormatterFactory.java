/*
 * My Project
 */

package com.ingsis.formatter.factories;

import com.ingsis.formatter.InMemoryProgramFormatter;
import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.formatter.handlers.factories.InMemoryFormatterHandlerFactory;
import com.ingsis.formatter.publishers.factories.InMemoryFormatterPublisherFactory;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.factory.DefaultResultFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.EventsChecker;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;
import com.ingsis.utils.rule.status.provider.RuleStatusProvider;
import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.runtime.result.factory.LoggerResultFactory;
import java.io.InputStream;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class InMemoryFormatterFactory implements FormatterFactory {
    private final SafeIteratorFactory<Interpretable> checkableIteratorFactory;

    public InMemoryFormatterFactory(SafeIteratorFactory<Interpretable> checkableIteratorFactory) {
        this.checkableIteratorFactory = checkableIteratorFactory;
    }

    @Override
    public ProgramFormatter fromFile(
            InputStream inputStream,
            Runtime runtime,
            RuleStatusProvider ruleStatusProvider,
            Writer writer) {
        ResultFactory resultFactory = new LoggerResultFactory(new DefaultResultFactory(), runtime);
        AtomicReference<Checker> checkerRef = new AtomicReference<>();
        Supplier<Checker> checkerSupplier = checkerRef::get;
        HandlerFactory handlerFactory =
                new InMemoryFormatterHandlerFactory(
                        resultFactory, ruleStatusProvider, checkerSupplier, writer);
        PublishersFactory publishersFactory = new InMemoryFormatterPublisherFactory(handlerFactory);
        Checker eventsChecker = new EventsChecker(publishersFactory);
        checkerRef.set(eventsChecker);
        return new InMemoryProgramFormatter(
                checkableIteratorFactory.fromInputStream(inputStream), eventsChecker, writer);
    }
}
