/*
 * My Project
 */

package com.ingsis.runtime.environment.entries.factories;

import java.util.List;
import java.util.Map;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.types.Types;

public interface EntryFactory {
  FunctionEntry createFunctionEntry(Types returnType, Map<String, Types> arguments, List<ExpressionNode> body);

  VariableEntry createVariableEntry(Types type, Object value);
}
