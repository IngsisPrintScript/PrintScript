package com.ingsis.runtime.environment.entries;

import java.util.List;
import java.util.Map;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.types.Types;

public interface FunctionEntry {

  Types returnType();

  Map<String, Types> arguments();

  List<ExpressionNode> body();
}
