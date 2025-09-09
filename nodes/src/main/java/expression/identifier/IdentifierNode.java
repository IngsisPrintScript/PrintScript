package expression.identifier;


import common.Environment;
import common.Node;
import expression.ExpressionNode;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import visitor.RuleVisitor;
import visitor.VisitorInterface;

import java.util.List;

public record IdentifierNode(String name) implements Node, ExpressionNode {

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
        try {
            Result<Object> getIdValue = Environment.getInstance().getIdValue(this.name());
            if (!getIdValue.isSuccessful()){
                return new IncorrectResult<>(getIdValue.errorMessage());
            }
            Object identifierValue = getIdValue.result();
            return new CorrectResult<>(identifierValue);
        } catch (Exception e) {
            return new IncorrectResult<>(e.getMessage());
        }
    }

    @Override
    public Result<String> prettyPrint() {
        return new CorrectResult<>(this.name());
    }

    @Override
    public Result<String> acceptCheck(RuleVisitor checker) {
        return checker.check(this);
    }
}
