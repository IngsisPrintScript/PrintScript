/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;

public final class DivitionSolutionStrategy implements ExpressionSolutionStrategy {
    private final ExpressionSolutionStrategy nextStrategy;

    public DivitionSolutionStrategy(ExpressionSolutionStrategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    private boolean canSolve(ExpressionNode expressionNode) {
        String symbol = expressionNode.symbol();
        return symbol.equals("/");
    }

    private Result<Object> divideByNewObject(Object old, Object delta) {
        try {
            double oldVal = Double.parseDouble(old.toString());
            double deltaVal = Double.parseDouble(delta.toString());
            return new CorrectResult<>(oldVal / deltaVal);
        } catch (NumberFormatException e) {
            return new IncorrectResult<>(
                    "Cannot divide non-numeric values: " + old + " and " + delta);
        }
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!canSolve(expressionNode)) {
            return nextStrategy.solve(interpreter, expressionNode);
        }
        List<ExpressionNode> children = expressionNode.children();
        Object result = null;
        for (ExpressionNode child : children) {
            Result<Object> solveChildExpression = interpreter.interpret(child);
            if (!solveChildExpression.isCorrect()) {
                return new IncorrectResult<>(solveChildExpression);
            }
            Object childResult = solveChildExpression.result();
            if (result == null) {
                result = childResult;
                continue;
            }
            Result<Object> divideResult = divideByNewObject(result, childResult);
            if (!divideResult.isCorrect()) {
                return new IncorrectResult<>(divideResult);
            }
            result = divideResult.result();
        }
        return new CorrectResult<>(result);
    }
}
