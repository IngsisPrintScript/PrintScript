package Semantic.SemanticHandler;

import Semantic.SemanticVisitor.SemanticVisitor;
import Semantic.Context.SemanticVisitorContext;
import common.nodes.Node;
import common.nodes.expression.binary.AdditionNode;
import common.responses.CorrectResult;
import common.responses.Result;

public class AdditionExpressionNode implements SemanticHandler<AdditionNode>{
    @Override
    public Result handleSemantic(AdditionNode node, SemanticVisitorContext context, SemanticVisitor visitor) {
        return new CorrectResult<>(new BinaryExpressionHandler().handleSemantic(node, context,visitor));
    }

    @Override
    public boolean canHandle(Node node) {
        return node instanceof AdditionNode;
    }
}
