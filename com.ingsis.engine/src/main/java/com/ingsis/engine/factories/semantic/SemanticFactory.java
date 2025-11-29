/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.parser.semantic.SemanticChecker;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface SemanticFactory {
    SemanticChecker fromInputStream(InputStream in) throws IOException;

    SemanticChecker fromFile(Path path) throws IOException;

    SemanticChecker fromString(CharSequence input) throws IOException;
}
