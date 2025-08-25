package Semantic.SemanticHandler;

import Semantic.SemanticVisitor.SemanticVisitor;
import Semantic.Context.SemanticVisitorContext;
import common.nodes.Node;
import common.nodes.declaration.IdentifierNode;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.Result;

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
