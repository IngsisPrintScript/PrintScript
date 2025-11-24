package com.ingsis.formatter.handlers;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.rule.observer.handlers.NodeEventHandler;

public class FormatterIdentifierHandler implements NodeEventHandler<ExpressionNode> {
  private final ResultFactory resultFactory;

  public FormatterIdentifierHandler(ResultFactory resultFactory) {
    this.resultFactory = resultFactory;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof IdentifierNode identifierNode)) {
      return resultFactory.createIncorrectResult("Incorrect handler.");
    }
    return resultFactory.createCorrectResult(identifierNode.name());
  }

}
