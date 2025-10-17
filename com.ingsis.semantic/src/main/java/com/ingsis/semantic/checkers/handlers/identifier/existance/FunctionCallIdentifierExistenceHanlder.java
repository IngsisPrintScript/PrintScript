package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.function.CallFunctionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;

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
    return new CorrectResult<>("Function is correctly declared & initialized before call");
  }

}
