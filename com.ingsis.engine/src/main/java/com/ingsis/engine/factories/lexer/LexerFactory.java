/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.lexer.Lexer;
import com.ingsis.result.factory.ResultFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface LexerFactory {
    Lexer fromInputStream(InputStream in, ResultFactory resultFactory) throws IOException;

    Lexer fromFile(Path path, ResultFactory resultFactory) throws IOException;

    Lexer fromString(CharSequence input, ResultFactory resultFactory) throws IOException;
}
