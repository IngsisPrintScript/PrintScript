package common;

import expression.ExpressionNode;
import responses.IncorrectResult;
import responses.Result;
import visitor.VisitorInterface;

import java.util.List;

public class NilNode implements Node, ExpressionNode {
    @Override
    public List<Node> children() {
        return List.of();
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }

    @Override
    public Boolean isNil() {
        return true;
    }

    @Override
    public Object evaluate() {
        throw new UnsupportedOperationException("Nil node can't be evaluated.");
    }
}
