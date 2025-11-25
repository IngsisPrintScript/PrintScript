/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.syntactic.DefaultSyntacticParser;
import com.ingsis.syntactic.SyntacticParser;
import com.ingsis.syntactic.factories.ParserChainFactory;
import java.io.IOException;
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
    public SyntacticParser createCliSyntacticChecker(String input) throws IOException {
        return new DefaultSyntacticParser(
                tokenStreamFactory.createCliTokenStream(input),
                parserChainFactory.createDefaultChain());
    }

    @Override
    public SyntacticParser createFileSyntacticChecker(Path filePath) throws IOException {
        return new DefaultSyntacticParser(
                tokenStreamFactory.createFileTokenStream(filePath),
                parserChainFactory.createDefaultChain());
    }

    @Override
    public SyntacticParser createReplSyntacticChecker() throws IOException {
        return new DefaultSyntacticParser(
                tokenStreamFactory.createReplTokenStream(),
                parserChainFactory.createDefaultChain());
    }
}
