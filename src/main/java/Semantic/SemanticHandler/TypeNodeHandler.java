package Semantic.SemanticHandler;

import Semantic.SemanticVisitor.SemanticVisitor;
import Semantic.Context.SemanticVisitorContext;
import common.nodes.Declaration.TypeNode.TypeNode;
import common.nodes.Node;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.Result;

public class TypeNodeHandler implements SemanticHandler<TypeNode> {
    @Override
    public Result handleSemantic(TypeNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        return new CorrectResult<>(node);
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof TypeNode;
    }
}
