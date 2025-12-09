/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.atomic.literal;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.List;

public record StringLiteralNode(String value, TokenStream stream, Integer line, Integer column)
        implements LiteralNode {

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public List<ExpressionNode> children() {
        return List.of();
    }

    @Override
    public String symbol() {
        return value();
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<Object> interpretResult = interpreter.interpret(this);
        if (!interpretResult.isCorrect()) {
            return new IncorrectResult<>(interpretResult);
        }
        return new CorrectResult<>("Interpreted successfully.");
    }

    @Override
    public Result<Object> solve() {
        return new CorrectResult<>(value());
    }
}
