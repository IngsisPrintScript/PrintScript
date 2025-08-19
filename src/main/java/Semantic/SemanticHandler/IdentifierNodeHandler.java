package Semantic.SemanticHandler;

import Semantic.SemanticVisitor.SemanticVisitor;
import Semantic.Context.SemanticVisitorContext;
import common.nodes.Node;
import common.nodes.Declaration.Identifier.IdentifierNode;
import common.responses.CorrectResult;
import common.responses.Result;

public class IdentifierNodeHandler implements SemanticHandler<IdentifierNode> {
    @Override
    public Result handleSemantic(IdentifierNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        return new CorrectResult<>(node.value());
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof IdentifierNode;
    }
}
