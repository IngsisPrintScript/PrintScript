/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.identifier;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.visitors.Interpreter;

public class IdentifierSolutionStrategy implements ExpressionSolutionStrategy {
    private final Runtime runtime;
    private final ExpressionSolutionStrategy nextStrategy;

    public IdentifierSolutionStrategy(ExpressionSolutionStrategy nextStrategy, Runtime runtime) {
        this.runtime = runtime;
        this.nextStrategy = nextStrategy;
    }

    private boolean canSolve(ExpressionNode expressionNode) {
        return expressionNode instanceof IdentifierNode;
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!canSolve(expressionNode)) {
            return nextStrategy.solve(interpreter, expressionNode);
        }
        IdentifierNode identifierNode = (IdentifierNode) expressionNode;
        Result<VariableEntry> getVarEntry =
                runtime.getCurrentEnvironment().getVariable(identifierNode.name());
        if (!getVarEntry.isCorrect()) {
            return new IncorrectResult<>(getVarEntry);
        }
        Object value = getVarEntry.result().value();
        return new CorrectResult<Object>(value);
    }
}
