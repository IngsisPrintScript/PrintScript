package common.nodes.expression.binary;

import common.nodes.Node;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class AdditionNode extends BinaryExpression{
    @Override
    public Result accept(VisitorInterface visitor) {
        Result visitResult = visitor.visit(this);

        if (!visitor.managesVisitFlow()) {
            for (Node child : children) {
                Result childResult = child.accept(visitor);
                if (!childResult.isSuccessful()){
                    return childResult;
                }
            }
        }

        return visitResult;
    }
}
