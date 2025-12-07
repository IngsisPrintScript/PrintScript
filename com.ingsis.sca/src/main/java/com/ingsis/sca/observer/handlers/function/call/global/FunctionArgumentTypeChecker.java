/*
 * My Project
 */

package com.ingsis.sca.observer.handlers.function.call.global;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.expressions.function.CallFunctionNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import java.util.List;

public class FunctionArgumentTypeChecker implements NodeEventHandler<ExpressionNode> {
  private final ResultFactory resultFactory;
  private final String functionExpectedName;
  private final List<Class<? extends ExpressionNode>> allowedArgumentTypes;

  public FunctionArgumentTypeChecker(
      ResultFactory resultFactory,
      String functionExpectedName,
      List<Class<? extends ExpressionNode>> allowedArgumentTypes) {
    this.resultFactory = resultFactory;
    this.functionExpectedName = functionExpectedName;
    this.allowedArgumentTypes = allowedArgumentTypes;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    if (!(node instanceof CallFunctionNode callFunctionNode)) {
      return resultFactory.createCorrectResult("Check does not apply to that node.");
    }

    String functionActualName = callFunctionNode.identifierNode().name();

    if (!functionActualName.equals(functionExpectedName)) {
      return resultFactory.createCorrectResult("Check passed.");
    }

    List<ExpressionNode> arguments = callFunctionNode.argumentNodes();

    for (ExpressionNode argument : arguments) {
      if (!allowedArgumentTypes.contains(argument.getClass())) {
        return resultFactory.createIncorrectResult(
            String.format(
                "%s function does not accept argument number: %d type: %s"
                    + "on line: %d and columnd: %d",
                functionExpectedName,
                arguments.indexOf(argument),
                argument.getClass().toString(),
                argument.line(),
                argument.column()));
      }
    }
    return resultFactory.createCorrectResult("Check passed.");
  }
}
