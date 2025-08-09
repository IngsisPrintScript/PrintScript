package nodes.expressions.composite;

import nodes.AbstractSyntaxTreeComposite;
import responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

public class AdditionExpressionNode extends AbstractSyntaxTreeComposite {
    @Override
    public Response accept(AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
}
