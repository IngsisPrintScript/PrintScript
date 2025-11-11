/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.lexer.DefaultLexer;
import com.ingsis.lexer.Lexer;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.result.factory.LoggerResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public final class DefaultLexerFactory implements LexerFactory {
    private final CharStreamFactory charStreamFactory;
    private final TokenizerFactory tokenizerFactory;
    private final Runtime runtime;

    public DefaultLexerFactory(
            CharStreamFactory charStreamFactory,
            TokenizerFactory tokenizerFactory,
            Runtime runtime) {
        this.charStreamFactory = charStreamFactory;
        this.tokenizerFactory = tokenizerFactory;
        this.runtime = runtime;
    }

    @Override
    public Lexer createCliLexer(Queue<Character> buffer, ResultFactory resultFactory) {
        return new DefaultLexer(
                charStreamFactory.inMemoryCharIterator(buffer),
                tokenizerFactory.createTokenizer(),
                new LoggerResultFactory(resultFactory, runtime));
    }

    @Override
    public Lexer createFromFileLexer(Path filePath, ResultFactory resultFactory)
            throws IOException {
        return new DefaultLexer(
                charStreamFactory.fromFileCharIterator(filePath),
                tokenizerFactory.createTokenizer(),
                new LoggerResultFactory(resultFactory, runtime));
    }
}
