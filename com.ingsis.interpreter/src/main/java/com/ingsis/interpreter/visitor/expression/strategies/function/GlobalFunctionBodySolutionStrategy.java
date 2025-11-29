/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.function;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.runtime.Runtime;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.function.GlobalFunctionBody;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressFBWarnings(
        value = "EI2",
        justification = "Runtime is intentionally passed and stored; design needs mutability.")
public final class GlobalFunctionBodySolutionStrategy implements ExpressionSolutionStrategy {
    private final Runtime RUNTIME;
    private final ExpressionSolutionStrategy NEXT_STRATEGY;

    public GlobalFunctionBodySolutionStrategy(
            Runtime runtime, ExpressionSolutionStrategy nextStrategy) {
        this.RUNTIME = runtime;
        this.NEXT_STRATEGY = nextStrategy;
    }

    @Override
    public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
        if (!(expressionNode instanceof GlobalFunctionBody globalFunctionBody)) {
            return NEXT_STRATEGY.solve(interpreter, expressionNode);
        }
        List<String> argNames = globalFunctionBody.argNames();
        Function<Object[], Object> lambda = globalFunctionBody.lambda();
        List<Object> parameters = new ArrayList<>();
        for (String argName : argNames) {
            Result<VariableEntry> getVariableResult =
                    RUNTIME.getCurrentEnvironment().readVariable(argName);
            if (!getVariableResult.isCorrect()) {
                return new IncorrectResult<>("Missing needed param for function execution.");
            }
            parameters.add(getVariableResult.result().value());
        }
        Object functionEvaluation = lambda.apply(parameters.toArray());
        return new CorrectResult<Object>(functionEvaluation);
    }
}
