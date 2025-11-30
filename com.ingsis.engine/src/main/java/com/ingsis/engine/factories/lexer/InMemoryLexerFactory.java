/*
 * My Project
 */

package com.ingsis.engine.factories.lexer;

import com.ingsis.engine.factories.charstream.CharStreamFactory;
import com.ingsis.lexer.DefaultLexer;
import com.ingsis.lexer.Lexer;
import com.ingsis.lexer.tokenizers.factories.TokenizerFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.InputStream;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Environment is a shared, controlled mutable dependency.")
public final class InMemoryLexerFactory implements LexerFactory {
    private final CharStreamFactory charStreamFactory;
    private final TokenizerFactory tokenizerFactory;

    public InMemoryLexerFactory(
            CharStreamFactory charStreamFactory, TokenizerFactory tokenizerFactory) {
        this.charStreamFactory = charStreamFactory;
        this.tokenizerFactory = tokenizerFactory;
    }

    @Override
    public Lexer fromInputStream(InputStream in, ResultFactory resultFactory) {
        return new DefaultLexer(
                charStreamFactory.fromInputStream(in),
                tokenizerFactory.createTokenizer(),
                resultFactory);
    }
}
