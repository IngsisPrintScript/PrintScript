/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.keyword; /*
                                               * My Project
                                               */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;
import java.util.List;

public record IfKeywordNode(
        ExpressionNode condition,
        List<Node> thenBody,
        List<Node> elseBody,
        Integer line,
        Integer column)
        implements Node, Checkable, Interpretable {

    public IfKeywordNode {
        thenBody = List.copyOf(thenBody);
        elseBody = List.copyOf(elseBody);
    }

    public IfKeywordNode(
            ExpressionNode condition, List<Node> thenBody, Integer line, Integer column) {
        this(condition, thenBody, List.of(), line, column);
    }

    @Override
    public List<Node> thenBody() {
        return List.copyOf(thenBody);
    }

    @Override
    public List<Node> elseBody() {
        return List.copyOf(elseBody);
    }

    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        return interpreter.interpret(this);
    }

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
