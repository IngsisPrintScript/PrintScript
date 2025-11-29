/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.literal;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.typer.string.DefaultStringTypeGetter;
import com.ingsis.utils.type.types.Types;

public class LiteralSolutionStrategy implements ExpressionSolutionStrategy {
    private ExpressionSolutionStrategy nextStrategy;

    public LiteralSolutionStrategy(ExpressionSolutionStrategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    private Result<Object> getNewObject(String input) {
        DefaultStringTypeGetter stringTypeGetter = new DefaultStringTypeGetter();
        Types inputType = stringTypeGetter.getType(input);

        if (inputType == Types.STRING) {
            return new CorrectResult<>(input);
        } else if (inputType == Types.NUMBER) {
            double val = Double.parseDouble(input);
            return new CorrectResult<>(val);
        } else if (inputType == Types.BOOLEAN) {
            boolean val = Boolean.parseBoolean(input);
            return new CorrectResult<>(val);
        }
        return new IncorrectResult<>("Unmanaged case.");
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!(expressionNode instanceof LiteralNode literalNode)) {
            return nextStrategy.solve(interpreter, expressionNode);
        }
        return getNewObject(literalNode.value());
    }
}
