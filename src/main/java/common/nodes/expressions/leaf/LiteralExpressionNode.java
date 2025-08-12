package common.nodes.expressions.leaf;

import common.nodes.AbstractSyntaxTreeLeaf;
import common.responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

public class LiteralExpressionNode extends AbstractSyntaxTreeLeaf {
    public LiteralExpressionNode(String value) {
        super(value);
    }

    @Override
    public Response accept(AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
}
