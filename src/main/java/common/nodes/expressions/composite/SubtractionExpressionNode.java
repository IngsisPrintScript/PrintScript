package common.nodes.expressions.composite;

import common.nodes.AbstractSyntaxTreeComposite;
import common.responses.Response;

public class SubtractionExpressionNode extends AbstractSyntaxTreeComposite {

    @Override
    public Response accept(visitors.AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
}
