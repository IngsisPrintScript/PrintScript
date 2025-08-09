package nodes.expressions.leaf;

import nodes.AbstractSyntaxTreeLeaf;
import responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

public class TypeExpressionNode extends AbstractSyntaxTreeLeaf {
    public TypeExpressionNode(String value) {
        super(value);
    }

    @Override
    public Response accept(AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
}
