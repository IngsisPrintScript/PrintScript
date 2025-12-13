/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeSequenceResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.IdentifierNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import java.util.function.Supplier;

public class CallFunctionParser implements Parser<ExpressionNode> {
  private final TokenTemplate lParen;
  private final TokenTemplate comma;
  private final TokenTemplate rParen;
  private final Parser<ExpressionNode> identifierParser;
  private final Supplier<Parser<ExpressionNode>> expressionParserSupplier;
  private final NodeFactory nodeFactory;
  private final NodePriority nodePriority;

  public CallFunctionParser(
      TokenTemplate lParen,
      TokenTemplate comma,
      TokenTemplate rParen,
      Parser<ExpressionNode> identifierParser,
      Supplier<Parser<ExpressionNode>> expressionParserSupplier,
      NodeFactory nodeFactory) {
    this.lParen = lParen;
    this.comma = comma;
    this.rParen = rParen;
    this.identifierParser = identifierParser;
    this.expressionParserSupplier = expressionParserSupplier;
    this.nodeFactory = nodeFactory;
    this.nodePriority = NodePriority.CALL_FUNCTION;
  }

  @Override
  public ParseResult<ExpressionNode> parse(TokenStream stream) {
    ParseResult<ExpressionNode> parseIdentifier = identifierParser.parse(stream);
    return switch (parseIdentifier) {
      case ParseResult.INVALID<ExpressionNode> I -> new ParseResult.INVALID<>();
      case ParseResult.PREFIX<ExpressionNode> P -> new ParseResult.PREFIX<>(nodePriority);
      case ParseResult.COMPLETE<ExpressionNode> C -> parseArguments(C);
    };
  }

  private ParseResult<ExpressionNode> parseArguments(
      ParseResult.COMPLETE<ExpressionNode> previousResult) {
    if (!(previousResult.node() instanceof IdentifierNode identifierNode)) {
      return new ParseResult.INVALID<>();
    }
    return switch (previousResult
        .finalStream()
        .consumeFullSequence(lParen, comma, rParen, expressionParserSupplier.get())) {
      case ConsumeSequenceResult.INCORRECT<ExpressionNode> I ->
        new ParseResult.PREFIX<>(nodePriority);
      case ConsumeSequenceResult.CORRECT<ExpressionNode> C ->
        new ParseResult.COMPLETE<>(
            nodeFactory.createCallFunctionNode(identifierNode, C.parsedNodes()),
            C.stream(),
            nodePriority,
            true);
    };
  }
}
