/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.parser.syntactic.SyntacticParser;
import java.io.InputStream;

public interface SyntacticFactory {
    SyntacticParser fromInputStream(InputStream in);
}
