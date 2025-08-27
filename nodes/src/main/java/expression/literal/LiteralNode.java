package expression.literal;


import common.LeafNode;
import responses.Result;
import visitor.VisitorInterface;

public class LiteralNode extends LeafNode {
    public LiteralNode(String value) {
        super(value);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
