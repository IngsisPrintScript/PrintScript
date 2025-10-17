package com.ingsis.typer.function;

import com.ingsis.nodes.expression.function.CallFunctionNode;
import com.ingsis.runtime.Runtime;
import com.ingsis.typer.TypeGetter;
import com.ingsis.types.Types;

public class DefaultFunctionTypeGetter implements TypeGetter<CallFunctionNode> {
  private final Runtime RUNTIME;

  public DefaultFunctionTypeGetter(Runtime RUNTIME) {
    this.RUNTIME = RUNTIME;
  }

  @Override
  public Types getType(CallFunctionNode expressionNode) {
    String functionIdentifier = expressionNode.identifierNode().name();
    return RUNTIME.getCurrentEnvironment().readFunction(functionIdentifier).result().returnType();
  }

}
