package expression.binary;


import common.responses.Result;
import visitor.VisitorInterface;

public class AdditionNode extends BinaryExpression{
    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
