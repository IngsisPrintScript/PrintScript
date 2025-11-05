/*
 * My Project
 */

package com.ingsis.syntactic.factories;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.syntactic.parsers.DefaultParserRegistry;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.syntactic.parsers.ParserRegistry;
import com.ingsis.syntactic.parsers.factories.ParserFactory;

public final class DefaultParserChainFactory implements ParserChainFactory {
  private final ParserFactory PARSER_FACTORY;
  private static Parser<Node> cachedChain;

  public DefaultParserChainFactory(ParserFactory PARSER_FACTORY) {
    this.PARSER_FACTORY = PARSER_FACTORY;
  }

  @Override
  public Parser<Node> createDefaultChain() {
    if (cachedChain != null) {
      return cachedChain;
    }
    ParserRegistry<Node> registry = new DefaultParserRegistry<>(createExpressionChain());
    cachedChain = registry;
    registerKeywordsParsers(registry);
    return registry;
  }

  @Override
  public Parser<ExpressionNode> createExpressionChain() {
    ParserRegistry<ExpressionNode> registry = new DefaultParserRegistry<>();
    registry.registerParser(PARSER_FACTORY.createLineExpressionParser());
    registry.registerParser(PARSER_FACTORY.createCallFunctionParser());
    return registry;
  }

  private void registerKeywordsParsers(ParserRegistry<Node> registry) {
    registry.registerParser(PARSER_FACTORY.createDeclarationParser());
    registry.registerParser(PARSER_FACTORY.createConditionalParser());
    registry.registerParser(PARSER_FACTORY.createCallFunctionParser());
  }
}
