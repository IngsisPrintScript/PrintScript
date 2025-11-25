/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.lexer.Lexer;
import com.ingsis.result.factory.ResultFactory;
import java.io.IOException;
import java.nio.file.Path;

public interface LexerFactory {
    Lexer createCliLexer(String input, ResultFactory resultFactory) throws IOException;

    Lexer createFromFileLexer(Path filePath, ResultFactory resultFactory) throws IOException;

    Lexer createReplLexer(ResultFactory resultFactory) throws IOException;
}
