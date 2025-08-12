package common.nodes.expressions.composite;

public class DivideExpressionNode extends nodes.AbstractSyntaxTreeComposite {
    @Override
    public responses.Response accept(visitors.AbstractSyntaxNodeVisitorInterface visitor) {
        return visitor.visit(this);
    }
}
