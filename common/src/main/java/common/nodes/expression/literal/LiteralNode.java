package common.nodes.expression.literal;

import common.nodes.LeafNode;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class LiteralNode extends LeafNode {
    public LiteralNode(String value) {
        super(value);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}
