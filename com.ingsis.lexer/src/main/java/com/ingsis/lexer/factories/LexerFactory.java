/*
 * My Project
 */

package com.ingsis.lexer.factories;

import com.ingsis.lexer.Lexer;
import com.ingsis.lexer.LoggerLexer;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.token.Token;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.InputStream;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public final class LexerFactory implements SafeIteratorFactory<Token> {
    private final SafeIteratorFactory<MetaChar> metaCharIterableFactory;
    private final TokenizerFactory tokenizerFactory;
    private final IterationResultFactory iterationResultFactory;

    public LexerFactory(
            SafeIteratorFactory<MetaChar> metaCharIterableFactory,
            TokenizerFactory tokenizerFactory,
            IterationResultFactory iterationResultFactory) {
        this.metaCharIterableFactory = metaCharIterableFactory;
        this.tokenizerFactory = tokenizerFactory;
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterator<Token> fromInputStream(InputStream in) {
        return new Lexer(
                metaCharIterableFactory.fromInputStream(in),
                tokenizerFactory.createTriviaTokenizer(),
                tokenizerFactory.createTokenizer(),
                iterationResultFactory);
    }

    @Override
    public SafeIterator<Token> fromInputStreamLogger(InputStream in, String debugPath) {
        try {
            return new LoggerLexer(
                    new Lexer(
                            metaCharIterableFactory.fromInputStreamLogger(in, debugPath),
                            tokenizerFactory.createTriviaTokenizer(),
                            tokenizerFactory.createTokenizer(),
                            iterationResultFactory),
                    debugPath);
        } catch (Exception ignored) {
            throw new RuntimeException();
        }
    }
}
