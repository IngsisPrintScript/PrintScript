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
import com.ingsis.utils.type.typer.string.DefaultStringTypeGetter;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public final class AdditionSolutionStrategy implements ExpressionSolutionStrategy {
    private final ExpressionSolutionStrategy nextStrategy;

    public AdditionSolutionStrategy(ExpressionSolutionStrategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    private boolean canSolve(ExpressionNode expressionNode) {
        if (expressionNode.isTerminalNode()) {
            return false;
        }
        String symbol = expressionNode.symbol();
        return symbol.equals("+");
    }

    private Result<Object> addNewObject(Object old, Object delta) {
        DefaultStringTypeGetter stringTypeGetter = new DefaultStringTypeGetter();
        Types oldType = stringTypeGetter.getType(old.toString());

        if (oldType == Types.STRING) {
            return new CorrectResult<>(old.toString() + delta.toString());
        } else if (oldType == Types.NUMBER) {
            try {
                double oldVal = Double.parseDouble(old.toString());
                double deltaVal = Double.parseDouble(delta.toString());
                return new CorrectResult<>(oldVal + deltaVal);
            } catch (NumberFormatException e) {
                return new IncorrectResult<>(
                        "Cannot add non-numeric values: " + old + " and " + delta);
            }
        } else if (oldType == Types.BOOLEAN) {
            boolean oldVal = Boolean.parseBoolean(old.toString());
            boolean deltaVal = Boolean.parseBoolean(delta.toString());
            return new CorrectResult<>(oldVal || deltaVal);
        }
        return new IncorrectResult<>("Unmanaged case.");
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
            Result<Object> addResult = addNewObject(result, childResult);
            if (!addResult.isCorrect()) {
                return new IncorrectResult<>(addResult);
            }
            result = addResult.result();
        }
        return new CorrectResult<>(result);
    }
}
