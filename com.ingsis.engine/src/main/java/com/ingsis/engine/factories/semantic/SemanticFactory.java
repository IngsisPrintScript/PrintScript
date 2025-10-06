/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.SemanticChecker;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface SemanticFactory {
    SemanticChecker createCliSemanticChecker(Queue<Character> buffer, Runtime runtime);

    SemanticChecker createFileSemanticChecker(Path filePath, Runtime runtime) throws IOException;
}
