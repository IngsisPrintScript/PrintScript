/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.InterpretResult;

import java.util.List;

public interface ExpressionStrategy {
  InterpretResult solve(List<ExpressionNode> arguments, EvalState evalState);
}
