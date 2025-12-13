/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers.atomic;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;

public class IdentifierParser implements Parser<ExpressionNode> {
  private final TokenTemplate identifierTemplate;
  private final NodeFactory nodeFactory;

  public IdentifierParser(TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
    this.identifierTemplate = tokenTemplateFactory.identifier();
    this.nodeFactory = nodeFactory;
  }

  @Override
  public ParseResult<ExpressionNode> parse(TokenStream stream) {
    return switch (stream.consume(identifierTemplate)) {
      case ConsumeResult.CORRECT C -> createCompleteResult(C);
      case ConsumeResult.INCORRECT I -> new ParseResult.INVALID<>();
    };
  }

  private ParseResult.COMPLETE<ExpressionNode> createCompleteResult(ConsumeResult.CORRECT C) {
    return new ParseResult.COMPLETE<>(
        nodeFactory.createIdentifierNode(
            C.consumedToken().value(),
            C.consumedToken().line(),
            C.consumedToken().column()),
        C.finalTokenStream(),
        NodePriority.IDENTIFIER,
        false);
  }
}
