/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import java.util.List;

public class AssignationStrategy implements ExpressionStrategy {

    @Override
    public Result<Object> solve(List<ExpressionNode> arguments) {
        Result<VariableEntry> getVariable =
                DefaultRuntime.getInstance()
                        .getCurrentEnvironment()
                        .readVariable(arguments.get(0).symbol());
        if (!getVariable.isCorrect()) {
            return new IncorrectResult<>(getVariable);
        }
        Result<Object> solveExpression = arguments.get(1).solve();
        if (!solveExpression.isCorrect()) {
            return new IncorrectResult<>(solveExpression);
        }
        DefaultRuntime.getInstance()
                .getCurrentEnvironment()
                .updateVariable(arguments.get(0).symbol(), solveExpression.result());
        return new CorrectResult<Object>(null);
    }
}
