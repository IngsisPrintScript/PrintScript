/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;

public interface ExpressionSolutionStrategy {
    Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode);
}
