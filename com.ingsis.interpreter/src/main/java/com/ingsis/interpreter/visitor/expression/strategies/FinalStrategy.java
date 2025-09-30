/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;

public final class FinalStrategy implements ExpressionSolutionStrategy {
    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        return new IncorrectResult<>("There was no strategy able to solve that expression.");
    }
}
