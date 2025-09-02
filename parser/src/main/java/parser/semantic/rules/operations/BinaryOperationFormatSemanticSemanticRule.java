package parser.semantic.rules.operations;

import nodes.common.Node;
import nodes.expression.ExpressionNode;
import nodes.expression.binary.BinaryExpression;
import nodes.expression.identifier.IdentifierNode;
import nodes.expression.literal.LiteralNode;
import nodes.factories.NodeFactory;
import parser.semantic.rules.type.ExpressionTypeGetter;
import parser.semantic.rules.type.ExpressionTypeGetterInterface;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public class BinaryOperationFormatSemanticSemanticRule extends OperationFormatSemanticRule {
    private static final Node template = new NodeFactory().createAdditionNode();
    private static final ExpressionTypeGetterInterface typeGetter = new ExpressionTypeGetter();
    public BinaryOperationFormatSemanticSemanticRule() {}

    @Override
    public boolean match(Node node) {
        return node instanceof BinaryExpression;
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (!(nodeToCheck instanceof BinaryExpression binaryExpression)){
            return new IncorrectResult<>("This rule does not apply to the received node.");
        }
        Result<ExpressionNode> getLeftNodeResult = binaryExpression.getLeftChild();
        if (!getLeftNodeResult.isSuccessful()) {
            return new IncorrectResult<>("This rule does not apply to the received node.");
        }
        ExpressionNode leftChild = getLeftNodeResult.result();
        String expectedType;
        if (leftChild instanceof LiteralNode literalLeftNode) {
            expectedType =  typeGetter.getType(literalLeftNode);
        } else if (leftChild instanceof IdentifierNode identifierLeftNode) {
            expectedType = typeGetter.getType(identifierLeftNode);
        } else {
            boolean leftChildIsCorrectlyFormatted = new OperationFormatSemanticRule().checkRules(nodeToCheck).isSuccessful();
            if (!leftChildIsCorrectlyFormatted) {
                return new IncorrectResult<>("This node does not pass the check.");
            }
            expectedType = typeGetter.getType(leftChild);
        }

        Result<ExpressionNode> getRightNodeResult = binaryExpression.getRightChild();
        if (!getRightNodeResult.isSuccessful()) {
            return new IncorrectResult<>("This rule does not apply to the received node.");
        }
        Node rightChild = getRightNodeResult.result();
        if (rightChild instanceof LiteralNode literalRightNode) {
            if (!expectedType.equals(typeGetter.getType(literalRightNode))) {
                return new IncorrectResult<>("This node does not pass the check.");
            }
        } else if (rightChild instanceof IdentifierNode identifierRightNode ) {
            if (!expectedType.equals(typeGetter.getType(identifierRightNode))) {
                return new IncorrectResult<>("This node does not pass the check.");
            }
        } else {
            boolean rightChildIsCorrectlyFormatted = new OperationFormatSemanticRule().checkRules(nodeToCheck).isSuccessful();
            if (!rightChildIsCorrectlyFormatted) {
                return new IncorrectResult<>("This node does not pass the check.");
            }
            if (!expectedType.equals(typeGetter.getType(rightChild))) {
                return new IncorrectResult<>("This node does not pass the check.");
            }
        }
        return new CorrectResult<String>("This node passes the check.");
    }
}
