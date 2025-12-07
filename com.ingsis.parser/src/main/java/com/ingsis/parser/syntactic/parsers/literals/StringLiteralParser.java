package com.ingsis.parser.syntactic.parsers.literals;

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

public class StringLiteralParser implements Parser<ExpressionNode> {
  private final TokenTemplate stringLiteralTemplate;
  private final NodeFactory nodeFactory;

  public StringLiteralParser(TokenTemplateFactory tokenTemplateFactory, NodeFactory nodeFactory) {
    this.stringLiteralTemplate = tokenTemplateFactory.stringLiteral();
    this.nodeFactory = nodeFactory;
  }

  @Override
  public ProcessCheckpoint<Token, ProcessResult<ExpressionNode>> parse(TokenStream stream) {
    SafeIterationResult<Token> consumeLiteralResult = stream.consume(stringLiteralTemplate);
    if (!consumeLiteralResult.isCorrect()) {
      return ProcessCheckpoint.UNINITIALIZED();
    }
    return ProcessCheckpoint.INITIALIZED(
        consumeLiteralResult.nextIterator(),
        ProcessResult.COMPLETE(
            nodeFactory.createStringLiteralNode(
                consumeLiteralResult.iterationResult().value(),
                consumeLiteralResult.iterationResult().line(),
                consumeLiteralResult.iterationResult().column()),
            NodePriority.IDENTIFIER.priority()));
  }

}
