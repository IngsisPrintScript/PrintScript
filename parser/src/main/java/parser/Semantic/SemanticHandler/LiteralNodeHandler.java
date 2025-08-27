package parser.Semantic.SemanticHandler;


import common.Node;
import responses.CorrectResult;
import responses.Result;
import expression.literal.LiteralNode;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

public class LiteralNodeHandler implements SemanticHandler<LiteralNode> {
    @Override
    public Result handleSemantic(LiteralNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        return new CorrectResult<>(node);
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof LiteralNode;
    }
}
