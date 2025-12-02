/*
 * My Project
 */

package com.ingsis.parser.syntactic.factories;

import com.ingsis.parser.syntactic.parsers.DefaultParserRegistry;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.parsers.ParserRegistry;
import com.ingsis.parser.syntactic.parsers.factories.ParserFactory;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import java.util.function.Supplier;

public final class DefaultParserChainFactory implements ParserChainFactory {

    private final ParserFactory parserFactory;

    public DefaultParserChainFactory(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    @Override
    public Parser<Node> createDefaultChain() {
        ParserRegistry<Node> registry = new DefaultParserRegistry<>(createExpressionChain());
        Supplier<Parser<Node>> lazyChainSupplier = () -> registry;
        registry.registerParser(parserFactory.createDeclarationParser());
        registry.registerParser(parserFactory.createConditionalParser(lazyChainSupplier));
        registry.registerParser(createExpressionChain());
        return registry;
    }

    @Override
    public Parser<ExpressionNode> createExpressionChain() {
        ParserRegistry<ExpressionNode> registry = new DefaultParserRegistry<>();
        registry.registerParser(parserFactory.createLineExpressionParser());
        return registry;
    }
}
