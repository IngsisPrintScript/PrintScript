package common.nodes.declaration;

import common.nodes.LeafNode;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class IdentifierNode extends LeafNode {
    public IdentifierNode(String identifier) {
        super(identifier);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
