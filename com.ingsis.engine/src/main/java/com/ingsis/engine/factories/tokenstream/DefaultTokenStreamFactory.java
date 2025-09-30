/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.tokenstream.DefaultTokenStream;
import com.ingsis.tokenstream.TokenStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public class DefaultTokenStreamFactory implements TokenStreamFactory {
    private final LexerFactory lexerFactory;

    public DefaultTokenStreamFactory(LexerFactory lexerFactory) {
        this.lexerFactory = lexerFactory;
    }

    @Override
    public TokenStream createCliTokenStream(Queue<Character> buffer) {
        return new DefaultTokenStream(lexerFactory.createCliLexer(buffer));
    }

    @Override
    public TokenStream createFileTokenStream(Path filePath) throws IOException {
        return new DefaultTokenStream(lexerFactory.createFromFileLexer(filePath));
    }
}
