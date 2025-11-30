/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.parser.semantic.DefaultSemanticChecker;
import com.ingsis.parser.semantic.SemanticChecker;
import com.ingsis.parser.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.parser.semantic.checkers.publishers.factories.DefaultSemanticPublisherFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.factories.DefaultCheckerFactory;
import java.io.InputStream;

public final class DefaultSemanticFactory implements SemanticFactory {
    private final SyntacticFactory syntacticFactory;
    private final Runtime runtime;
    private final Checker checker;

    public DefaultSemanticFactory(
            SyntacticFactory syntacticFactory, ResultFactory resultFactory, Runtime runtime) {
        this.syntacticFactory = syntacticFactory;
        this.runtime = runtime;
        this.checker =
                new DefaultCheckerFactory()
                        .createInMemoryEventBasedChecker(
                                new DefaultSemanticPublisherFactory(
                                        new DefaultHandlersFactory(runtime, resultFactory)));
    }

    @Override
    public SemanticChecker fromInputStream(InputStream in) {
        return new DefaultSemanticChecker(syntacticFactory.fromInputStream(in), checker, runtime);
    }
}
