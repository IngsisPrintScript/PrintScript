/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.lexer.DefaultLexer;
import com.ingsis.lexer.Lexer;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public final class DefaultLexerFactory implements LexerFactory {
    private final CharStreamFactory charStreamFactory;
    private final TokenizerFactory tokenizerFactory;

    public DefaultLexerFactory(
            CharStreamFactory charStreamFactory, TokenizerFactory tokenizerFactory) {
        this.charStreamFactory = charStreamFactory;
        this.tokenizerFactory = tokenizerFactory;
    }

    @Override
    public Lexer createCliLexer(Queue<Character> buffer) {
        return new DefaultLexer(
                charStreamFactory.inMemoryCharIterator(buffer), tokenizerFactory.createTokenizer());
    }

    @Override
    public Lexer createFromFileLexer(Path filePath) throws IOException {
        return new DefaultLexer(
                charStreamFactory.fromFileCharIterator(filePath),
                tokenizerFactory.createTokenizer());
    }
}
