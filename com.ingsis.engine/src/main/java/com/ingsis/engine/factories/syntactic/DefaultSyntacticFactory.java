/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.parser.syntactic.DefaultSyntacticParser;
import com.ingsis.parser.syntactic.SyntacticParser;
import com.ingsis.parser.syntactic.factories.ParserChainFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class DefaultSyntacticFactory implements SyntacticFactory {
    private final TokenStreamFactory tokenStreamFactory;
    private final ParserChainFactory parserChainFactory;

    public DefaultSyntacticFactory(
            TokenStreamFactory tokenStreamFactory, ParserChainFactory parserFactory) {
        this.tokenStreamFactory = tokenStreamFactory;
        this.parserChainFactory = parserFactory;
    }

    @Override
    public SyntacticParser fromInputStream(InputStream in) throws IOException {
        return new DefaultSyntacticParser(
                tokenStreamFactory.fromInputStream(in), parserChainFactory.createDefaultChain());
    }

    @Override
    public SyntacticParser fromFile(Path path) throws IOException {
        return new DefaultSyntacticParser(
                tokenStreamFactory.fromFile(path), parserChainFactory.createDefaultChain());
    }

    @Override
    public SyntacticParser fromString(CharSequence input) throws IOException {
        return new DefaultSyntacticParser(
                tokenStreamFactory.fromString(input), parserChainFactory.createDefaultChain());
    }
}
