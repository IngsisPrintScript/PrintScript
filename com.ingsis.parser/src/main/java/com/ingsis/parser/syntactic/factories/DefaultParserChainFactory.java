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
        registry = registry.registerParser(parserFactory.lineExpressionParser(expressionParser));
        registryRef.set(registry);
        return registry;
    }

    @Override
    public Parser<ExpressionNode> createExpressionChain() {
        AtomicReference<Parser<ExpressionNode>> registryRef = new AtomicReference<>();
        AtomicReference<Parser<ExpressionNode>> leafRegistryRef = new AtomicReference<>();

        Parser<ExpressionNode> leafParser = createLeafExpressionChain();
        leafRegistryRef.set(leafParser);

        ParserRegistry<ExpressionNode> registry = new DefaultParserRegistry<>();
        registry = registry.registerParser(parserFactory.numberLiteralParser());
        registry = registry.registerParser(parserFactory.stringLiteralParser());
        registry = registry.registerParser(parserFactory.booleanLiteralParser());
        registry = registry.registerParser(parserFactory.identifierParser());
        registry =
                registry.registerParser(
                        parserFactory.callFunctionParser(
                                parserFactory.identifierParser(), () -> registryRef.get()));
        registry =
                registry.registerParser(parserFactory.operatorParser(() -> leafRegistryRef.get()));
        registryRef.set(registry);
        return registry;
    }

    private Parser<ExpressionNode> createLeafExpressionChain() {
        AtomicReference<Parser<ExpressionNode>> leafRegistryRef = new AtomicReference<>();
        ParserRegistry<ExpressionNode> leafRegistry = new DefaultParserRegistry<>();
        leafRegistry = leafRegistry.registerParser(parserFactory.numberLiteralParser());
        leafRegistry = leafRegistry.registerParser(parserFactory.stringLiteralParser());
        leafRegistry = leafRegistry.registerParser(parserFactory.booleanLiteralParser());
        leafRegistry = leafRegistry.registerParser(parserFactory.identifierParser());
        leafRegistry =
                leafRegistry.registerParser(
                        parserFactory.callFunctionParser(
                                parserFactory.identifierParser(), () -> leafRegistryRef.get()));
        // NO incluimos operatorParser aquí para evitar recursión infinita
        leafRegistryRef.set(leafRegistry);
        return leafRegistry;
    }
}
