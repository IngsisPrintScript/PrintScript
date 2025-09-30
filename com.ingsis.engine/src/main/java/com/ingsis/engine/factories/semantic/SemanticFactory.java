/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.semantic.SemanticChecker;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface SemanticFactory {
    SemanticChecker createCliSemanticChecker(Queue<Character> buffer);

    SemanticChecker createFileSemanticChecker(Path filePath) throws IOException;
}
