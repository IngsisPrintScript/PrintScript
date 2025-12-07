/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.function.CallFunctionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterFunctionCallHandler implements NodeEventHandler<ExpressionNode> {
  private final Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier;
  private final ResultFactory resultFactory;
  private final Writer writer;

  public FormatterFunctionCallHandler(
      Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier,
      ResultFactory resultFactory,
      Writer writer) {
    this.expressionHandlerSupplier = expressionHandlerSupplier;
    this.resultFactory = resultFactory;
    this.writer = writer;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof CallFunctionNode callFunctionNode)) {
      return resultFactory.createIncorrectResult("Incorrect handler.");
    }
    try {
      writer.append(callFunctionNode.symbol());
      writer.append("( ");
      Result<String> parseExpressionResult = formatArguments(callFunctionNode);
      if (!parseExpressionResult.isCorrect()) {
        return resultFactory.cloneIncorrectResult(parseExpressionResult);
      }
      writer.append(" )");
      writer.append(" ;");

    } catch (IOException e) {
      return resultFactory.createIncorrectResult(e.getMessage());
    }
    return resultFactory.createCorrectResult("Formatt correct.");
  }

  private Result<String> formatArguments(CallFunctionNode node) {
    for (ExpressionNode child : node.children()) {
      Result<String> formatChildResult = expressionHandlerSupplier.get().handle(child);
      if (!formatChildResult.isCorrect()) {
        return resultFactory.cloneIncorrectResult(formatChildResult);
      }
      try {
        writer.append(",");
      } catch (Exception e) {
        return resultFactory.createIncorrectResult(e.getMessage());
      }
    }
    return resultFactory.createCorrectResult("Arguments formatted correctly.");
  }
}
