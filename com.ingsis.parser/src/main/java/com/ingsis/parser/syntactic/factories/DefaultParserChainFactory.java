/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.parsers.DefaultParserRegistry;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.ParserRegistry;
import com.ingsis.parser.syntactic.parsers.factories.DefaultParserFactory;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.factories.DefaultNodeFactory;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;

public final class DefaultParserChainFactory implements ParserChainFactory {
    private final ParserFactory parserFactory;

    public DefaultParserChainFactory(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    private static class LazyHolder {
        static final Parser<Node> INSTANCE = buildStaticChain();
    }

    private static Parser<Node> buildStaticChain() {
        ParserFactory parserFactory =
                new DefaultParserFactory(new DefaultTokensFactory(), new DefaultNodeFactory());
        DefaultParserChainFactory chainFactory = new DefaultParserChainFactory(parserFactory);

        ParserRegistry<Node> registry =
                new DefaultParserRegistry<>(chainFactory.createExpressionChain());

        chainFactory.registerKeywordsParsers(registry);

        return registry;
    }

    @Override
    public Parser<Node> createDefaultChain() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public Parser<ExpressionNode> createExpressionChain() {
        ParserRegistry<ExpressionNode> registry = new DefaultParserRegistry<>();
        registry.registerParser(parserFactory.createLineExpressionParser());
        registry.registerParser(parserFactory.createCallFunctionParser());
        return registry;
    }

    private void registerKeywordsParsers(ParserRegistry<Node> registry) {
        registry.registerParser(parserFactory.createDeclarationParser());
        registry.registerParser(parserFactory.createConditionalParser(this));
    }
}
