/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.lexer.Lexer;
import com.ingsis.result.factory.ResultFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface LexerFactory {
    Lexer createCliLexer(Queue<Character> buffer, ResultFactory resultFactory);

    Lexer createFromFileLexer(Path filePath, ResultFactory resultFactory) throws IOException;
}
