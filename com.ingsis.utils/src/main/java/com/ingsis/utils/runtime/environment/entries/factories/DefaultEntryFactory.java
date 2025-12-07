package com.ingsis.utils.runtime.environment.entries.factories;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.runtime.environment.entries.DefaultFunctionEntry;
import com.ingsis.utils.runtime.environment.entries.DefaultVariableEntry;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;

/*
 * My Project
 */

import com.ingsis.utils.type.types.Types;
import java.util.List;
import java.util.Map;

public final class DefaultEntryFactory implements EntryFactory {
  @Override
  public VariableEntry createVariableEntry(Types type, Object value, Boolean isMutable) {
    return new DefaultVariableEntry(type, value, isMutable);
  }

  @Override
  public FunctionEntry createFunctionEntry(
      Types returnType,
      Map<String, Types> arguments,
      List<ExpressionNode> body,
      Environment closure) {
    return new DefaultFunctionEntry(returnType, arguments, body, closure);
  }
}
