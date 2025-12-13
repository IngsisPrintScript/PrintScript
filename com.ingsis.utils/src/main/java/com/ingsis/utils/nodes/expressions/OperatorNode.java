/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.List;

public record OperatorNode(
        OperatorType operatorType, List<ExpressionNode> children, Integer line, Integer column)
        implements ExpressionNode {

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<Object> interpretResult = interpreter.interpret(this);
        if (!interpretResult.isCorrect()) {
            return new IncorrectResult<>(interpretResult);
        }
        return new CorrectResult<String>("Interpreted correctly.");
    }

    @Override
    public String symbol() {
        return operatorType.symbol();
    }

    @Override
    public Result<Object> solve() {
        return operatorType().strategy().solve(children);
    }
}
