/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.lexer.Lexer;
import com.ingsis.utils.result.factory.ResultFactory;
import java.io.InputStream;

public interface LexerFactory {
    Lexer fromInputStream(InputStream in, ResultFactory resultFactory);
}
