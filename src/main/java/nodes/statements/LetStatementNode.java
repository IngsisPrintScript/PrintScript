package nodes.statements;

import nodes.AbstractSyntaxTreeComposite;
import responses.Response;
import visitors.AbstractSyntaxNodeVisitorInterface;

public class LetStatementNode extends AbstractSyntaxTreeComposite {
    @Override
    public Response accept(AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
}
