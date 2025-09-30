/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.engine.factories.syntactic.SyntacticFactory;
import com.ingsis.semantic.DefaultSemanticChecker;
import com.ingsis.semantic.SemanticChecker;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public final class DefaultSemanticFactory implements SemanticFactory {
    private final SyntacticFactory syntacticFactory;

    public DefaultSemanticFactory(SyntacticFactory syntacticFactory) {
        this.syntacticFactory = syntacticFactory;
    }

    @Override
    public SemanticChecker createCliSemanticChecker(Queue<Character> buffer) {
        return new DefaultSemanticChecker(syntacticFactory.createCliSyntacticChecker(buffer));
    }

    @Override
    public SemanticChecker createFileSemanticChecker(Path filePath) throws IOException {
        return new DefaultSemanticChecker(syntacticFactory.createFileSyntacticChecker(filePath));
    }
}
