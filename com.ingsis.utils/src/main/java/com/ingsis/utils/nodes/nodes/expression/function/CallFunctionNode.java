/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.function; /*
                                                           * My Project
                                                           */

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.util.ArrayList;
import java.util.List;

public record CallFunctionNode(
        IdentifierNode identifierNode,
        List<ExpressionNode> argumentNodes,
        Integer line,
        Integer column)
        implements ExpressionNode {

    public CallFunctionNode {
        argumentNodes = List.copyOf(argumentNodes);
    }

    @Override
    public List<ExpressionNode> argumentNodes() {
        return List.copyOf(argumentNodes);
    }

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
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
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public List<ExpressionNode> children() {
        List<ExpressionNode> children = new ArrayList<>();
        children.add(identifierNode);
        children.addAll(argumentNodes);
        return children;
    }

    @Override
    public Boolean isTerminalNode() {
        return true;
    }

    @Override
    public String symbol() {
        return identifierNode().name();
    }
}
