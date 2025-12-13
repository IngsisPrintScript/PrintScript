/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public record NilExpressionNode() implements ExpressionNode {

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<Object> interpretResult = interpreter.interpret(this);
        if (interpretResult.isCorrect()) {
            return new CorrectResult<String>("Interpreted correctly.");
        } else {
            return new IncorrectResult<>(interpretResult);
        }
    }

    @Override
    public List<ExpressionNode> children() {
        return List.of();
    }

    @Override
    public String symbol() {
        return Types.NIL.name();
    }

    @Override
    public Integer line() {
        throw new UnsupportedOperationException(
                "Nil expression node has no line where it was built from.");
    }

    @Override
    public Integer column() {
        throw new UnsupportedOperationException(
                "Nil expression node has no line where it was built from.");
    }

    @Override
    public Result<Object> solve() {
        return new CorrectResult<>(null);
    }
}
