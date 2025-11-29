/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.identifier;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
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
