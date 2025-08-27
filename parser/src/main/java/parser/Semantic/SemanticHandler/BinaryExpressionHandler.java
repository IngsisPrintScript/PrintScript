package parser.Semantic.SemanticHandler;

import common.responses.CorrectResult;
import common.responses.Result;
import expression.binary.BinaryExpression;
import parser.Semantic.Context.SemanticVisitorContext;
import parser.Semantic.SemanticVisitor.SemanticVisitor;

public class BinaryExpressionHandler {
    public Result handleSemantic(BinaryExpression node, SemanticVisitorContext context, SemanticVisitor visitor) {
        Result leftResult = node.leftChild();
        if (!leftResult.isSuccessful()) {
            return leftResult;
        }
        Result rightResult = node.rightChild();
        if (!rightResult.isSuccessful()) {
            return rightResult;
        }
        if (((CorrectResult<?>) leftResult).newObject() instanceof BinaryExpression binaryExpression) {
            leftResult = visitor.dispatch(binaryExpression);
        }
        if (((CorrectResult<?>) rightResult).newObject() instanceof BinaryExpression binaryExpression) {
            rightResult = visitor.dispatch(binaryExpression);
        }

        return context.semanticRules().checkSemanticRules(leftResult, rightResult, node);
    }
}
