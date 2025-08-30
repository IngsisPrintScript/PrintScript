package expression.identifier;


import common.Environment;
import common.Node;
import expression.ExpressionNode;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import visitor.VisitorInterface;

import java.util.List;

public record IdentifierNode(String name) implements Node, ExpressionNode {

    @Override
    public Result accept(VisitorInterface visitor) {
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
        try {
            Object identifierValue = Environment.getInstance().getIdValue(this.name()).result();
            return new CorrectResult<>(identifierValue);
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
    }

    @Override
    public Result<String> prettyPrint() {
        return new CorrectResult<>(this.name());
    }
}
