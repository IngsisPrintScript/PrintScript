/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.lexer.Lexer;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface LexerFactory {
    Lexer createCliLexer(Queue<Character> buffer);

    Lexer createFromFileLexer(Path filePath) throws IOException;
}
