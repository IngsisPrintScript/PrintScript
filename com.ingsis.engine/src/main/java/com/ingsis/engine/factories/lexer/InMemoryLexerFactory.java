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

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public final class InMemoryLexerFactory implements LexerFactory {
    private final CharStreamFactory charStreamFactory;
    private final TokenizerFactory tokenizerFactory;
    private final Runtime runtime;

    public InMemoryLexerFactory(
            CharStreamFactory charStreamFactory,
            TokenizerFactory tokenizerFactory,
            Runtime runtime) {
        this.charStreamFactory = charStreamFactory;
        this.tokenizerFactory = tokenizerFactory;
        this.runtime = runtime;
    }

    @Override
    public Lexer createCliLexer(String input, ResultFactory resultFactory) throws IOException {
        // Use fromString instead of inMemoryCharIterator
        return new DefaultLexer(
                charStreamFactory.fromString(input),
                tokenizerFactory.createTokenizer(),
                new LoggerResultFactory(resultFactory, runtime));
    }

    @Override
    public Lexer createFromFileLexer(Path filePath, ResultFactory resultFactory)
            throws IOException {
        return new DefaultLexer(
                charStreamFactory.fromFile(filePath),
                tokenizerFactory.createTokenizer(),
                new LoggerResultFactory(resultFactory, runtime));
    }

    @Override
    public Lexer createReplLexer(ResultFactory resultFactory) throws IOException {
        return new DefaultLexer(
                charStreamFactory.fromInputStream(System.in),
                tokenizerFactory.createTokenizer(),
                new LoggerResultFactory(resultFactory, runtime));
    }
}
