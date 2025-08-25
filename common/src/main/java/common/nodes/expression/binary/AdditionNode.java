package common.nodes.expression.binary;

import common.responses.Result;
import common.visitor.VisitorInterface;

public class AdditionNode extends BinaryExpression{
    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
