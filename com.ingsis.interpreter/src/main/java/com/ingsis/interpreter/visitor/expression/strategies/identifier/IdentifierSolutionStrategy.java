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
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public class IdentifierSolutionStrategy implements ExpressionSolutionStrategy {
    private final Runtime runtime;
    private final ExpressionSolutionStrategy nextStrategy;

    public IdentifierSolutionStrategy(ExpressionSolutionStrategy nextStrategy, Runtime runtime) {
        this.runtime = runtime;
        this.nextStrategy = nextStrategy;
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!(expressionNode instanceof IdentifierNode identifierNode)) {
            return nextStrategy.solve(interpreter, expressionNode);
        }
        Result<VariableEntry> getVarEntry =
                runtime.getCurrentEnvironment().readVariable(identifierNode.name());
        if (!getVarEntry.isCorrect()) {
            return new IncorrectResult<>(getVarEntry);
        }
        Object value = getVarEntry.result().value();
        return new CorrectResult<Object>(value);
    }
}
