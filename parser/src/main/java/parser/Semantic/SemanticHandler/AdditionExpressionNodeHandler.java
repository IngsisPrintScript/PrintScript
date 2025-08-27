package parser.Semantic.SemanticHandler;

import common.Node;
import responses.Result;
import expression.binary.AdditionNode;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

public class AdditionExpressionNodeHandler implements SemanticHandler<AdditionNode> {
    @Override
    public Result handleSemantic(AdditionNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        return new BinaryExpressionHandler().handleSemantic(node, context, visitor);
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof AdditionNode;
    }
}
