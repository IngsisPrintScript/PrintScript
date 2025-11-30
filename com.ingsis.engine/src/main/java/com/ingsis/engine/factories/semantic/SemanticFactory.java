/*
 * My Project
 */

package com.ingsis.engine.factories.semantic;

import com.ingsis.parser.semantic.SemanticChecker;
import java.io.InputStream;

public interface SemanticFactory {
    SemanticChecker fromInputStream(InputStream in);
}
