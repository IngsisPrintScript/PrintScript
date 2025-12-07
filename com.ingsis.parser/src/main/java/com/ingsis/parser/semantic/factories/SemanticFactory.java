/*
 * My Project
 */

package com.ingsis.parser.semantic.factories;

import com.ingsis.parser.semantic.SemanticChecker;
import com.ingsis.parser.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.parser.semantic.checkers.publishers.factories.DefaultSemanticPublisherFactory;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.factories.DefaultCheckerFactory;
import com.ingsis.utils.runtime.Runtime;
import java.io.InputStream;

public final class SemanticFactory implements SafeIteratorFactory<Interpretable> {
    private final SafeIteratorFactory<Checkable> checkableIteratorFactory;
    private final Runtime runtime;
    private final Checker checker;
    private final IterationResultFactory iterationResultFactory;

    public SemanticFactory(
            SafeIteratorFactory<Checkable> nodeIteratorFactory,
            ResultFactory resultFactory,
            Runtime runtime,
            IterationResultFactory iterationResultFactory) {
        this.checkableIteratorFactory = nodeIteratorFactory;
        this.runtime = runtime;
        this.checker =
                new DefaultCheckerFactory()
                        .createInMemoryEventBasedChecker(
                                new DefaultSemanticPublisherFactory(
                                        new DefaultHandlersFactory(runtime, resultFactory)));
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterator<Interpretable> fromInputStream(InputStream in) {
        return new SemanticChecker(
                checkableIteratorFactory.fromInputStream(in),
                checker,
                runtime,
                iterationResultFactory);
    }
}
