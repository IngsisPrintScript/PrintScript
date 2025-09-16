/*
 * My Project
 */

package com.ingsis.printscript.astnodes;

import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.visitor.RuleVisitor;
import com.ingsis.printscript.visitor.VisitorInterface;
import java.util.List;

public class NilNode implements Node, ExpressionNode {
    @Override
    public List<Node> children() {
        return List.of();
    }

    @Override
    public Result<String> accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public Boolean isNil() {
        return true;
    }

    @Override
    public Result<Object> evaluate() {
        throw new UnsupportedOperationException("Nil node can't be evaluated.");
    }

    @Override
    public Result<String> prettyPrint() {
        throw new UnsupportedOperationException("Nil node can't be printed.");
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        throw new UnsupportedOperationException("Nil node can't be acceptCheck.");
    }
}
