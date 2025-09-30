/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;

public interface ExpressionSolutionStrategy {
    Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode);
}
