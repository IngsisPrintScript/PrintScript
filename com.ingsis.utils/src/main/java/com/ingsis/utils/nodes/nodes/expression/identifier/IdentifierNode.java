/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.identifier; /*
                                                             * My Project
                                                             */

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
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
