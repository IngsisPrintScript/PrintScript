package parser.Semantic.SemanticHandler;


import common.Node;
import common.responses.CorrectResult;
import common.responses.Result;
import declaration.TypeNode;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

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
