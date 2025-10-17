package com.ingsis.semantic.checkers.handlers.identifier.existance;

import java.util.List;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.runtime.Runtime;

public class FunctionCallIdentifierExistenceHanlder implements NodeEventHandler<CallFunctionNode> {
  private final Runtime RUNTIME;

  public FunctionCallIdentifierExistenceHanlder(Runtime RUNTIME) {
    this.RUNTIME = RUNTIME;
  }

  @Override
  public Result<String> handle(CallFunctionNode node) {
    IdentifierNode identifierNode = node.identifierNode();
    String functionCalledName = identifierNode.name();
    if (!RUNTIME.getCurrentEnvironment().isFunctionInitialized(functionCalledName)) {
      return new IncorrectResult<>("Function is called before being initialized.");
    }
    NodeEventHandler<ExpressionNode> expressionHandler = new ExpressionNodeEventVariableExistenceHandler(RUNTIME);
    List<ExpressionNode> arguments = node.argumentNodes();
    for (ExpressionNode argument : arguments) {
      Result<String> handleExpression = expressionHandler.handle(argument);
      if (!handleExpression.isCorrect()) {
        return new IncorrectResult<>(handleExpression);
      }
    }

    return new CorrectResult<>("Function is correctly declared & initialized before call");
  }

}
