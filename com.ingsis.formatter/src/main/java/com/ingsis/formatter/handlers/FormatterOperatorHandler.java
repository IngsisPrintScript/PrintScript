/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.operator.OperatorNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterOperatorHandler implements NodeEventHandler<ExpressionNode> {
  private final ResultFactory resultFactory;
  private final Supplier<NodeEventHandler<ExpressionNode>> leafHandlerSupplier;
  private final Writer writer;

  public FormatterOperatorHandler(
      ResultFactory resultFactory,
      Supplier<NodeEventHandler<ExpressionNode>> leafHandlerSupplier,
      Writer writer) {
    this.resultFactory = resultFactory;
    this.leafHandlerSupplier = leafHandlerSupplier;
    this.writer = writer;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof OperatorNode operatorNode)) {
      return resultFactory.createIncorrectResult("Incorrect handler.");
    }
    Result<String> leftFormatResult = leafHandlerSupplier.get().handle(node.children().get(0));
    if (!leftFormatResult.isCorrect()) {
      return resultFactory.cloneIncorrectResult(leftFormatResult);
    }
    try {
      writer.append(" ");
      writer.append(operatorNode.symbol());
      writer.append(" ");
      Result<String> rightFormatResult = leafHandlerSupplier.get().handle(node.children().get(1));
      if (!rightFormatResult.isCorrect()) {
        return resultFactory.cloneIncorrectResult(rightFormatResult);
      }
      writer.append(rightFormatResult.result());
    } catch (IOException e) {
      return resultFactory.createIncorrectResult(e.getMessage());
    }
    return resultFactory.createCorrectResult("Formatt passed.");
  }
}
