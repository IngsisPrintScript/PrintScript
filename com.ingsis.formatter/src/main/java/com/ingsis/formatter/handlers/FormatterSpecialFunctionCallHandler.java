/*
 * My Project
 */

package com.ingsis.formatter.handlers;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class FormatterSpecialFunctionCallHandler implements NodeEventHandler<ExpressionNode> {
  private final Integer amountOfLinesBeforeCall;
  private final String functionName;
  private final Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier;
  private final ResultFactory resultFactory;
  private final Writer writer;

  public FormatterSpecialFunctionCallHandler(
      Integer amountOfLinesBeforeCall,
      String functionName,
      Supplier<NodeEventHandler<ExpressionNode>> expressionHandlerSupplier,
      ResultFactory resultFactory,
      Writer writer) {
    this.amountOfLinesBeforeCall = amountOfLinesBeforeCall;
    this.functionName = functionName;
    this.expressionHandlerSupplier = expressionHandlerSupplier;
    this.resultFactory = resultFactory;
    this.writer = writer;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof CallFunctionNode callFunctionNode)) {
      return resultFactory.createIncorrectResult("Incorrect handler.");
    }
    Result<String> baseFunctionFormatterHandleResult = new FormatterFunctionCallHandler(expressionHandlerSupplier,
        resultFactory,
        writer).handle(node);
    if (!baseFunctionFormatterHandleResult.isCorrect()) {
      return resultFactory.cloneIncorrectResult(baseFunctionFormatterHandleResult);
    }
    try {
      String functionIdentifier = callFunctionNode.identifierNode().name();
      if (functionIdentifier.equals(functionName)) {
        for (int i = 0; i < amountOfLinesBeforeCall; i++) {
          writer.append("\n");
        }
      } else {
        return resultFactory.createIncorrectResult("Incorrect handler.");
      }
    } catch (IOException e) {
      return resultFactory.createIncorrectResult(e.getMessage());
    }

    return resultFactory.createCorrectResult("Formatt correct.");
  }
}
