/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.atomic.identifier;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.DefaultRuntime;
import com.ingsis.utils.runtime.environment.entries.VariableEntry;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.List;

public record IdentifierNode(String name, TokenStream stream, Integer line, Integer column)
        implements ExpressionNode {

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
        return name();
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
        Result<VariableEntry> getVariableEntry =
                DefaultRuntime.getInstance().getCurrentEnvironment().readVariable(name());
        if (!getVariableEntry.isCorrect()) {
            return new IncorrectResult<>(getVariableEntry);
        }
        return new CorrectResult<>(getVariableEntry.result().value());
    }
}
