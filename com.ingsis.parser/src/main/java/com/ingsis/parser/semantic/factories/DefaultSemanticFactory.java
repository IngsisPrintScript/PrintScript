/*
 * My Project
 */

package com.ingsis.parser.semantic.factories;

import com.ingsis.parser.semantic.DefaultSemanticChecker;
import com.ingsis.parser.semantic.SemanticChecker;
import com.ingsis.parser.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.parser.semantic.checkers.publishers.factories.DefaultSemanticPublisherFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.peekableiterator.factories.PeekableIteratorFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.factories.DefaultCheckerFactory;
import java.io.InputStream;

public final class DefaultSemanticFactory implements PeekableIteratorFactory<Interpretable> {
    private final PeekableIteratorFactory<Checkable> checkableIteratorFactory;
    private final Runtime runtime;
    private final Checker checker;

    public DefaultSemanticFactory(
            PeekableIteratorFactory<Checkable> nodeIteratorFactory,
            ResultFactory resultFactory,
            Runtime runtime) {
        this.checkableIteratorFactory = nodeIteratorFactory;
        this.runtime = runtime;
        this.checker =
                new DefaultCheckerFactory()
                        .createInMemoryEventBasedChecker(
                                new DefaultSemanticPublisherFactory(
                                        new DefaultHandlersFactory(runtime, resultFactory)));
    }

    @Override
    public SemanticChecker fromInputStream(InputStream in) {
        return new DefaultSemanticChecker(
                checkableIteratorFactory.fromInputStream(in), checker, runtime);
    }
}
