/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.nil;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public record NilExpressionNode() implements ExpressionNode {

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptVisitor'");
    }

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
    public Boolean isTerminalNode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isTerminalNode'");
    }

    @Override
    public String symbol() {
        return Types.NIL.name();
    }

    @Override
    public Integer line() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'line'");
    }

    @Override
    public Integer column() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'column'");
    }
}
