/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.factory.ParserFactory;
import com.ingsis.parser.syntactic.parsers.registry.DefaultParserRegistry;
import com.ingsis.parser.syntactic.parsers.registry.ParserRegistry;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import java.util.concurrent.atomic.AtomicReference;

public final class DefaultParserChainFactory implements ParserChainFactory {

    private final ParserFactory parserFactory;

    public DefaultParserChainFactory(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    @Override
    public Parser<Node> createDefaultChain() {
        AtomicReference<Parser<Node>> registryRef = new AtomicReference<>();
        Parser<ExpressionNode> expressionParser = createExpressionChain();
        ParserRegistry<Node> registry = new DefaultParserRegistry<>();
        registry =
                registry.registerParser(
                        parserFactory.declarationParser(
                                parserFactory.identifierParser(), expressionParser));
        registry =
                registry.registerParser(
                        parserFactory.conditionalParser(expressionParser, () -> registryRef.get()));
        registryRef.set(registry);
        return registry;
    }

    @Override
    public Parser<ExpressionNode> createExpressionChain() {
        AtomicReference<Parser<ExpressionNode>> registryRef = new AtomicReference<>();
        ParserRegistry<ExpressionNode> registry = new DefaultParserRegistry<>();
        registry = registry.registerParser(parserFactory.numberLiteralParser());
        registry = registry.registerParser(parserFactory.stringLiteralParser());
        registry = registry.registerParser(parserFactory.booleanLiteralParser());
        registry = registry.registerParser(parserFactory.identifierParser());
        registry =
                registry.registerParser(
                        parserFactory.callFunctionParser(
                                parserFactory.identifierParser(), () -> registryRef.get()));
        registry = registry.registerParser(parserFactory.operatorParser(() -> registryRef.get()));
        registryRef.set(registry);
        return registry;
    }
}
