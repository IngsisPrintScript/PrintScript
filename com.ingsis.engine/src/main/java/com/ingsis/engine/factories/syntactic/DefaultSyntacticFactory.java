/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import com.ingsis.engine.factories.tokenstream.TokenStreamFactory;
import com.ingsis.parser.syntactic.DefaultSyntacticParser;
import com.ingsis.parser.syntactic.SyntacticParser;
import com.ingsis.parser.syntactic.factories.ParserChainFactory;
import java.io.InputStream;

public final class DefaultSyntacticFactory implements SyntacticFactory {
    private final TokenStreamFactory tokenStreamFactory;
    private final ParserChainFactory parserChainFactory;

    public DefaultSyntacticFactory(
            TokenStreamFactory tokenStreamFactory, ParserChainFactory parserFactory) {
        this.tokenStreamFactory = tokenStreamFactory;
        this.parserChainFactory = parserFactory;
    }

    @Override
    public SyntacticParser fromInputStream(InputStream in) {
        return new DefaultSyntacticParser(
                tokenStreamFactory.fromInputStream(in), parserChainFactory.createDefaultChain());
    }
}
