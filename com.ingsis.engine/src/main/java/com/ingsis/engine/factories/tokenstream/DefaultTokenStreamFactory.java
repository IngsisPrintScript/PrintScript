/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.io.InputStream;

public class DefaultTokenStreamFactory implements TokenStreamFactory {
    private final LexerFactory lexerFactory;
    private final ResultFactory resultFactory;

    public DefaultTokenStreamFactory(LexerFactory lexerFactory, ResultFactory resultFactory) {
        this.lexerFactory = lexerFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public TokenStream fromInputStream(InputStream in) {
        return new DefaultTokenStream(
                lexerFactory.fromInputStream(in, resultFactory), resultFactory);
    }
}
