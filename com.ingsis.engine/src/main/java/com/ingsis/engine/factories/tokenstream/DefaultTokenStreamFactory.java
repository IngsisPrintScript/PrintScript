/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokenstream.DefaultTokenStream;
import com.ingsis.tokenstream.TokenStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class DefaultTokenStreamFactory implements TokenStreamFactory {
    private final LexerFactory lexerFactory;
    private final ResultFactory resultFactory;

    public DefaultTokenStreamFactory(LexerFactory lexerFactory, ResultFactory resultFactory) {
        this.lexerFactory = lexerFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public TokenStream fromInputStream(InputStream in) throws IOException {
        return new DefaultTokenStream(
                lexerFactory.fromInputStream(in, resultFactory), resultFactory);
    }

    @Override
    public TokenStream fromFile(Path path) throws IOException {
        return new DefaultTokenStream(lexerFactory.fromFile(path, resultFactory), resultFactory);
    }

    @Override
    public TokenStream fromString(CharSequence input) throws IOException {
        return new DefaultTokenStream(lexerFactory.fromString(input, resultFactory), resultFactory);
    }
}
