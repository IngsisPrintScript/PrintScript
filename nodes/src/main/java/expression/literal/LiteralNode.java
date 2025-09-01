package expression.literal;


import common.Node;
import expression.ExpressionNode;
import results.CorrectResult;
import results.Result;
import visitor.RuleVisitor;
import visitor.VisitorInterface;

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
        return new CorrectResult<>(this.value);
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
