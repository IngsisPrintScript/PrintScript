package com.ingsis.utils.runtime.environment.entries.factories;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;

/*
 * My Project
 */

import com.ingsis.utils.type.types.Types;
import java.util.List;
import java.util.Map;

public interface EntryFactory {
  FunctionEntry createFunctionEntry(
      Types returnType,
      Map<String, Types> arguments,
      List<ExpressionNode> body,
      Environment closure);

  VariableEntry createVariableEntry(Types type, Object value, Boolean isMutable);
}
