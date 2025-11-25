/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.SemanticChecker;
import java.io.IOException;
import java.nio.file.Path;

public interface SemanticFactory {

    SemanticChecker createCliSemanticChecker(String input, Runtime runtime) throws IOException;

    SemanticChecker createFileSemanticChecker(Path filePath, Runtime runtime) throws IOException;

    SemanticChecker createReplSemanticChecker(Runtime runtime) throws IOException;
}
