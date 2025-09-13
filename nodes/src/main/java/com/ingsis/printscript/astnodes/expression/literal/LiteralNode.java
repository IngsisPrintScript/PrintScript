/*
 * My Project
 */

package com.ingsis.printscript.astnodes.expression.literal;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.visitor.RuleVisitor;
import com.ingsis.printscript.astnodes.visitor.VisitorInterface;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.Result;
import java.util.List;

public record LiteralNode(String value) implements Node, ExpressionNode {
    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<Node> children() {
        return List.of();
    }

    @Override
    public Boolean isNil() {
        return false;
    }

    @Override
    public Result<Object> evaluate() {
        return new CorrectResult<>(this.value());
    }

    @Override
    public Result<String> prettyPrint() {
        return new CorrectResult<>(this.value());
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }
}
