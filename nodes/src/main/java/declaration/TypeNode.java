package declaration;


import common.LeafNode;
import responses.Result;
import visitor.VisitorInterface;

public class TypeNode extends LeafNode {

    public TypeNode(String type) {
        super(type);
    }

    @Override
    public Result accept(VisitorInterface visitor) {
        return visitor.visit(this);
    }
}