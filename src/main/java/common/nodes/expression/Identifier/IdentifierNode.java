package common.nodes.expression.Identifier;

import common.nodes.LeafNode;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class IdentifierNode extends LeafNode {

    public IdentifierNode(String value) {
        super(value);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
