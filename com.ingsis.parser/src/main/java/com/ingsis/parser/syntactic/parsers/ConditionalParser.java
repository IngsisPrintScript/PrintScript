/*
 * My Project
 */

package com.ingsis.parser.syntactic.parsers;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.ParseResult;
import com.ingsis.parser.syntactic.tokenstream.TokenStream;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeResult;
import com.ingsis.parser.syntactic.tokenstream.results.ConsumeSequenceResult;
import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.token.template.TokenTemplate;
import java.util.List;
import java.util.function.Supplier;

public class ConditionalParser implements Parser<Node> {
  private final TokenTemplate iff;
  private final TokenTemplate elze;
  private final TokenTemplate lParen;
  private final TokenTemplate rParen;
  private final TokenTemplate lBrace;
  private final TokenTemplate rBrace;
  private final Parser<ExpressionNode> conditionParser;
  private final Supplier<Parser<Node>> parserSupplier;
  private final NodeFactory nodeFactory;
  private final NodePriority nodePriority;

  public ConditionalParser(
      TokenTemplate iff,
      TokenTemplate elze,
      TokenTemplate lParen,
      TokenTemplate rParen,
      TokenTemplate lBrace,
      TokenTemplate rBrace,
      Parser<ExpressionNode> conditionParser,
      Supplier<Parser<Node>> parserSupplier,
      NodeFactory nodeFactory) {
    this.iff = iff;
    this.elze = elze;
    this.lParen = lParen;
    this.rParen = rParen;
    this.lBrace = lBrace;
    this.rBrace = rBrace;
    this.conditionParser = conditionParser;
    this.parserSupplier = parserSupplier;
    this.nodeFactory = nodeFactory;
    this.nodePriority = NodePriority.STATEMENT;
  }

  @Override
  public ParseResult<Node> parse(TokenStream stream) {
    return switch (stream.consume(iff)) {
      case ConsumeResult.INCORRECT I -> new ParseResult.INVALID<>();
      case ConsumeResult.CORRECT C -> parseCondition(C);
    };
  }

  private ParseResult<Node> parseCondition(ConsumeResult.CORRECT in) {
    return switch (in.finalTokenStream()
        .consumeSequenceWithNoSeparator(lParen, rParen, conditionParser)) {
      case ConsumeSequenceResult.INCORRECT<ExpressionNode> I ->
        new ParseResult.PREFIX<>(nodePriority);
      case ConsumeSequenceResult.CORRECT<ExpressionNode> C ->
        parseThenBody(in.consumedToken().line(), in.consumedToken().column(), C);
    };
  }

  private ParseResult<Node> parseThenBody(
      int line, int column, ConsumeSequenceResult.CORRECT<ExpressionNode> in) {
    return switch (in.stream()
        .consumeSequenceWithNoSeparator(lBrace, rBrace, parserSupplier.get())) {
      case ConsumeSequenceResult.INCORRECT<Node> I -> new ParseResult.PREFIX<>(nodePriority);
      case ConsumeSequenceResult.CORRECT<Node> C ->
        parseElse(in.parsedNodes().get(0), line, column, C);
    };
  }

  private ParseResult<Node> parseElse(
      ExpressionNode condition,
      int line,
      int column,
      ConsumeSequenceResult.CORRECT<Node> in) {
    return switch (in.stream().consume(elze)) {
      case ConsumeResult.INCORRECT I ->
        createHalfIf(condition, in.parsedNodes(), line, column, in);
      case ConsumeResult.CORRECT C ->
        parseElseBody(condition, in.parsedNodes(), line, column, C);
    };
  }

  private ParseResult<Node> parseElseBody(
      ExpressionNode condition,
      List<Node> ifBody,
      int line,
      int column,
      ConsumeResult.CORRECT in) {
    return switch (in.finalTokenStream()
        .consumeSequenceWithNoSeparator(lBrace, rBrace, parserSupplier.get())) {
      case ConsumeSequenceResult.INCORRECT<Node> I -> new ParseResult.PREFIX<>(nodePriority);
      case ConsumeSequenceResult.CORRECT<Node> C ->
        createFullIf(condition, ifBody, line, column, C);
    };
  }

  private ParseResult<Node> createHalfIf(
      ExpressionNode condition,
      List<Node> ifBody,
      int line,
      int column,
      ConsumeSequenceResult.CORRECT<Node> in) {
    return new ParseResult.COMPLETE<Node>(
        nodeFactory.createConditionalNode(condition, ifBody, List.of(), line, column),
        in.stream(),
        nodePriority,
        false);
  }

  private ParseResult<Node> createFullIf(
      ExpressionNode condition,
      List<Node> ifBody,
      int line,
      int column,
      ConsumeSequenceResult.CORRECT<Node> in) {
    return new ParseResult.COMPLETE<Node>(
        nodeFactory.createConditionalNode(
            condition, ifBody, in.parsedNodes(), line, column),
        in.stream(),
        nodePriority,
        true);
  }
}
