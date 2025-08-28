package declaration;


import common.LeafNode;
import responses.Result;
import visitor.VisitorInterface;

public class IdentifierNode extends LeafNode {
    public IdentifierNode(String identifier) {
        super(identifier);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
