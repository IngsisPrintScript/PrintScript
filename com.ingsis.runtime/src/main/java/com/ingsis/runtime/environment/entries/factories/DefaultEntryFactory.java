/*
 * My Project
 */

package com.ingsis.runtime.environment.entries.factories;

import java.util.List;
import java.util.Map;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.runtime.environment.entries.DefaultFunctionEntry;
import com.ingsis.runtime.environment.entries.DefaultVariableEntry;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;

public final class DefaultEntryFactory implements EntryFactory {
  @Override
  public VariableEntry createVariableEntry(Types type, Object value) {
    return new DefaultVariableEntry(type, value);
  }

  @Override
  public FunctionEntry createFunctionEntry(Types returnType, Map<String, Types> arguments,
      List<ExpressionNode> body) {
    return new DefaultFunctionEntry(returnType, arguments, body);
  }
}
