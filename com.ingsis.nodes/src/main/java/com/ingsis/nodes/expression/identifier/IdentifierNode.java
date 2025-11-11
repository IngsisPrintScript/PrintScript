/*
 * My Project
 */

package com.ingsis.nodes.expression.identifier;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;
import java.util.List;

public record IdentifierNode(String name, Integer line, Integer column) implements ExpressionNode {

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<ExpressionNode> children() {
        return List.of();
    }

    @Override
    public Boolean isTerminalNode() {
        return true;
    }

    @Override
    public String symbol() {
        throw new UnsupportedOperationException("IdentifierNode.symbol() is not supported.");
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        Result<Object> interpretResult = interpreter.interpret(this);
        if (!interpretResult.isCorrect()) {
            return new IncorrectResult<>(interpretResult);
        }
        return new CorrectResult<>("Interpreted successfully.");
    }
}
