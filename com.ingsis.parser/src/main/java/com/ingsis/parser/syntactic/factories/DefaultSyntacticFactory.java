/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.DefaultSyntacticParser;
import com.ingsis.parser.syntactic.SyntacticParser;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.peekableiterator.factories.PeekableIteratorFactory;
import com.ingsis.utils.token.tokens.Token;
import java.io.InputStream;

public final class DefaultSyntacticFactory implements PeekableIteratorFactory<Checkable> {
    private final PeekableIteratorFactory<Token> tokenIteratorFactory;
    private final ParserChainFactory parserChainFactory;

    public DefaultSyntacticFactory(
            PeekableIteratorFactory<Token> tokenIteratorFactory, ParserChainFactory parserFactory) {
        this.tokenIteratorFactory = tokenIteratorFactory;
        this.parserChainFactory = parserFactory;
    }

    @Override
    public SyntacticParser fromInputStream(InputStream in) {
        return new DefaultSyntacticParser(
                tokenIteratorFactory.fromInputStream(in), parserChainFactory.createDefaultChain());
    }
}
