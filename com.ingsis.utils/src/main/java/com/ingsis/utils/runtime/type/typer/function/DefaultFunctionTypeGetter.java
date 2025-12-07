package com.ingsis.utils.runtime.type.typer.function;

import com.ingsis.utils.nodes.expressions.function.CallFunctionNode;

/*
 * My Project
 */

import com.ingsis.utils.type.typer.TypeGetter;
import com.ingsis.utils.type.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import com.ingsis.utils.runtime.Runtime;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class DefaultFunctionTypeGetter implements TypeGetter<CallFunctionNode> {
  private final Runtime RUNTIME;

  public DefaultFunctionTypeGetter(Runtime RUNTIME) {
    this.RUNTIME = RUNTIME;
  }

  @Override
  public Types getType(CallFunctionNode expressionNode) {
    String functionIdentifier = expressionNode.identifierNode().name();
    return RUNTIME.getCurrentEnvironment()
        .readFunction(functionIdentifier)
        .result()
        .returnType();
  }
}
