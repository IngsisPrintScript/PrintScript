package parser.Semantic.SemanticHandler;


import common.Node;
import common.responses.Result;
import declaration.IdentifierNode;
import expression.literal.LiteralNode;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

public class IdentifierNodeHandler implements SemanticHandler<IdentifierNode> {
    @Override
    public Result handleSemantic(IdentifierNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        return context.variablesTable().getValue(new LiteralNode(node.value()));
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof IdentifierNode;
    }
}
