/*
 * My Project
 */

package com.ingsis.lexer.factories;

import com.ingsis.lexer.DefaultLexer;
import com.ingsis.lexer.Lexer;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.factories.PeekableIteratorFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.InputStream;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public final class InMemoryLexerFactory implements PeekableIteratorFactory<Token> {
    private final PeekableIteratorFactory<MetaChar> metaCharIterableFactory;
    private final TokenizerFactory tokenizerFactory;
    private final ResultFactory resultFactory;

    public InMemoryLexerFactory(
            PeekableIteratorFactory<MetaChar> metaCharIterableFactory,
            TokenizerFactory tokenizerFactory,
            ResultFactory resultFactory) {
        this.metaCharIterableFactory = metaCharIterableFactory;
        this.tokenizerFactory = tokenizerFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public Lexer fromInputStream(InputStream in) {
        return new DefaultLexer(
                metaCharIterableFactory.fromInputStream(in),
                tokenizerFactory.createTokenizer(),
                resultFactory);
    }
}
