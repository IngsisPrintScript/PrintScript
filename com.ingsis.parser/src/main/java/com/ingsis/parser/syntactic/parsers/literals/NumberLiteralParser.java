package com.ingsis.parser.syntactic.parsers.literals;

import java.math.BigDecimal;

import com.ingsis.parser.syntactic.NodePriority;
import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.factories.NodeFactory;
import com.ingsis.utils.process.checkpoint.ProcessCheckpoint;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.template.TokenTemplate;
import com.ingsis.utils.token.template.factories.TokenTemplateFactory;
import com.ingsis.utils.token.tokenstream.TokenStream;

public class NumberLiteralParser implements Parser<ExpressionNode> {
  private final TokenTemplate numberLiteralTemplate;
  private final NodeFactory nodeFactory;

  public NumberLiteralParser(TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
    this.numberLiteralTemplate = tokenTemplateFactory.numberLiteral();
    this.nodeFactory = nodeFactory;
  }

  @Override
  public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
    SafeIterationResult<Token> consumeLiteralResult = stream.consume(numberLiteralTemplate);
    if (!consumeLiteralResult.isCorrect()) {
      return ProcessCheckpoint.UNINITIALIZED();
    }
    return ProcessCheckpoint.INITIALIZED(
        consumeLiteralResult.nextIterator(),
        ProcessResult.COMPLETE(
            nodeFactory.createNumberLiteralNode(
                new BigDecimal(consumeLiteralResult.iterationResult().value()),
                consumeLiteralResult.iterationResult().line(),
                consumeLiteralResult.iterationResult().column()),
            NodePriority.LITERAL.priority()));
  }

}
