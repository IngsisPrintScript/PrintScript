/*
 * My Project
 */

package com.ingsis.engine.factories.tokenstream;

import com.ingsis.engine.factories.lexer.LexerFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokenstream.DefaultTokenStream;
import com.ingsis.tokenstream.TokenStream;
import java.io.IOException;
import java.nio.file.Path;

public class DefaultTokenStreamFactory implements TokenStreamFactory {
    private final LexerFactory lexerFactory;
    private final ResultFactory resultFactory;

    public DefaultTokenStreamFactory(LexerFactory lexerFactory, ResultFactory resultFactory) {
        this.lexerFactory = lexerFactory;
        this.resultFactory = resultFactory;
    }

    @Override
    public TokenStream createCliTokenStream(String input) throws IOException {
        return new DefaultTokenStream(
                lexerFactory.createCliLexer(input, resultFactory), resultFactory);
    }

    @Override
    public TokenStream createFileTokenStream(Path filePath) throws IOException {
        return new DefaultTokenStream(
                lexerFactory.createFromFileLexer(filePath, resultFactory), resultFactory);
    }

    public TokenStream createReplTokenStream() throws IOException {
        return new DefaultTokenStream(lexerFactory.createReplLexer(resultFactory), resultFactory);
    }
}
