package com.ingsis.formatter.handlers;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public class FormatterLiteralHandler implements NodeEventHandler<ExpressionNode> {
  private final ResultFactory resultFactory;

  public FormatterLiteralHandler(ResultFactory resultFactory) {
    this.resultFactory = resultFactory;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof LiteralNode literalNode)) {
      return resultFactory.createIncorrectResult("Incorrect handler.");
    }
    return resultFactory.createCorrectResult(literalNode.value());
  }

}
