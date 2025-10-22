/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.types.Types;
import java.util.List;
import java.util.Map;

public record DefaultFunctionEntry(
    Types returnType,
    Map<String, Types> arguments,
    List<ExpressionNode> body,
    Environment closure) implements FunctionEntry {

  public DefaultFunctionEntry {
    arguments = Map.copyOf(arguments);
    body = List.copyOf(body);
  }
}
