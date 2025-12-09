/*
 * My Project
 */

package com.ingsis.utils.nodes.keyword;

import com.ingsis.utils.nodes.Node;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokenstream.TokenStream;

import java.util.List;

public record IfKeywordNode(
        ExpressionNode condition,
        List<Node> thenBody,
        List<Node> elseBody,
        TokenStream stream,
        Integer line,
        Integer column)
        implements Node, Interpretable {

    public IfKeywordNode {
        thenBody = List.copyOf(thenBody);
        elseBody = List.copyOf(elseBody);
    }

    public IfKeywordNode(
            ExpressionNode condition, List<Node> thenBody, TokenStream stream, Integer line, Integer column) {
        this(condition, thenBody, List.of(), stream, line, column);
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
}
