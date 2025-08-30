package parser.semantic.rules.operations;

import common.Node;
import expression.binary.BinaryExpression;
import expression.literal.LiteralNode;
import factories.NodeFactory;
import parser.semantic.rules.type.ExpressionTypeGetter;
import parser.semantic.rules.type.ExpressionTypeGetterInterface;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;

public class BinaryOperationFormatSemanticSemanticRule extends OperationFormatSemanticRule {
    private static final Node template = new NodeFactory().createAdditionNode();
    private static final ExpressionTypeGetterInterface typeGetter = new ExpressionTypeGetter();
    public BinaryOperationFormatSemanticSemanticRule() {}

    @Override
    public boolean match(Node node) {
        return node.equals(template);
    }

    @Override
    public Result checkRules(Node nodeToCheck) {
        if (!(nodeToCheck instanceof BinaryExpression binaryExpression)){
            return new IncorrectResult("This rule does not apply to the received node.");
        }
        Result getLeftNodeResult = binaryExpression.leftChild();
        if (!getLeftNodeResult.isSuccessful()) return getLeftNodeResult;
        Node leftChild = ((CorrectResult<Node>) getLeftNodeResult).result();
        String expectedType;
        if (leftChild instanceof LiteralNode literalLeftNode) {
            expectedType =  typeGetter.getType(literalLeftNode);
        } else {
            boolean leftChildIsCorrectlyFormatted = new OperationFormatSemanticRule().checkRules(nodeToCheck).isSuccessful();
            if (!leftChildIsCorrectlyFormatted) {
                return new IncorrectResult("This node does not pass the check.");
            }
            expectedType = typeGetter.getType(leftChild);
        }

        Result getRightNodeResult = binaryExpression.rightChild();
        if (!getRightNodeResult.isSuccessful()) return getRightNodeResult;
        Node rightChild = ((CorrectResult<Node>) getRightNodeResult).result();
        if (rightChild instanceof LiteralNode literalRightNode) {
            if (!expectedType.equals(typeGetter.getType(literalRightNode))) {
                return new IncorrectResult("This node does not pass the check.");
            }
        } else {
            boolean rightChildIsCorrectlyFormatted = new OperationFormatSemanticRule().checkRules(nodeToCheck).isSuccessful();
            if (!rightChildIsCorrectlyFormatted) {
                return new IncorrectResult("This node does not pass the check.");
            }
            if (!expectedType.equals(typeGetter.getType(rightChild))) {
                return new IncorrectResult("This node does not pass the check.");
            }
        }
        return new CorrectResult<String>("This node passes the check.");
    }
}
