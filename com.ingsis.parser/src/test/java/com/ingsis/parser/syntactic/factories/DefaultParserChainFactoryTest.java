/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.utils.nodes.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.nodes.nodes.factories.NodeFactory;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import org.junit.jupiter.api.Test;

class DefaultParserChainFactoryTest {

    @Test
    void lazyHolderAndChains() {
        TokenFactory tf = new DefaultTokensFactory();
        NodeFactory nf = new DefaultNodeFactory();

        DefaultParserFactory factory = new DefaultParserFactory(tf, nf);
        DefaultParserChainFactory chainFactory = new DefaultParserChainFactory(factory);

        // createDefaultChain should initialize LazyHolder.INSTANCE and return a parser
        Parser<?> p1 = chainFactory.createDefaultChain();
        assertNotNull(p1);

        // repeated calls should return the same singleton instance
        Parser<?> p2 = chainFactory.createDefaultChain();
        assertSame(p1, p2);

        // createExpressionChain returns a non-null parser (registry)
        Parser<?> expr = chainFactory.createExpressionChain();
        assertNotNull(expr);
    }
}
