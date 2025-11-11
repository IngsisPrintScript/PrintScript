/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.DefaultSemanticChecker;
import com.ingsis.semantic.SemanticChecker;
import com.ingsis.semantic.checkers.factories.DefaultCheckerFactory;
import com.ingsis.semantic.checkers.handlers.factories.DefaultHandlersFactory;
import com.ingsis.semantic.checkers.publishers.factories.DefaultPublisherFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public final class DefaultSemanticFactory implements SemanticFactory {
    private final SyntacticFactory syntacticFactory;
    private final ResultFactory resultFactory;

    public DefaultSemanticFactory(SyntacticFactory syntacticFactory, ResultFactory resultFactory) {
        this.syntacticFactory = syntacticFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public SemanticChecker createCliSemanticChecker(Queue<Character> buffer, Runtime runtime) {
        return new DefaultSemanticChecker(
                syntacticFactory.createCliSyntacticChecker(buffer),
                new DefaultCheckerFactory()
                        .createInMemoryEventBasedChecker(
                                new DefaultPublisherFactory(
                                        new DefaultHandlersFactory(runtime, resultFactory))),
                runtime);
    }

    @Override
    public SemanticChecker createFileSemanticChecker(Path filePath, Runtime runtime)
            throws IOException {
        return new DefaultSemanticChecker(
                syntacticFactory.createFileSyntacticChecker(filePath),
                new DefaultCheckerFactory()
                        .createInMemoryEventBasedChecker(
                                new DefaultPublisherFactory(
                                        new DefaultHandlersFactory(runtime, resultFactory))),
                runtime);
    }
}
