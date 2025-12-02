/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import java.io.IOException;
import java.io.Writer;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

public class FormatterIdentifierHandler implements NodeEventHandler<ExpressionNode> {
  private final ResultFactory resultFactory;
  private final Writer writer;

  public FormatterIdentifierHandler(ResultFactory resultFactory, Writer writer) {
    this.resultFactory = resultFactory;
    this.writer = writer;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof IdentifierNode identifierNode)) {
      return resultFactory.createIncorrectResult("Incorrect handler.");
    }
    try {
      writer.write(identifierNode.name());
    } catch (IOException e) {
      resultFactory.createIncorrectResult(e.getMessage());
    }
    return resultFactory.createCorrectResult("Formatt passed.");
  }
}
