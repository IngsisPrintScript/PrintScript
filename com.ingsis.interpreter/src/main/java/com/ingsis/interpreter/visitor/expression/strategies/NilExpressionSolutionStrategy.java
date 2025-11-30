/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.nil.NilExpressionNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.Result;

public final class NilExpressionSolutionStrategy implements ExpressionSolutionStrategy {
    private final ExpressionSolutionStrategy nextStrategy;

    public NilExpressionSolutionStrategy(ExpressionSolutionStrategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (expressionNode instanceof NilExpressionNode) {
            return new CorrectResult<>(null);
        }
        return nextStrategy.solve(interpreter, expressionNode);
    }
}
