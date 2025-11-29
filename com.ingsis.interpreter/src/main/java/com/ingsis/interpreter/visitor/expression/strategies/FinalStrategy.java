/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public final class FinalStrategy implements ExpressionSolutionStrategy {
    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        return new IncorrectResult<>("There was no strategy able to solve that expression.");
    }
}
