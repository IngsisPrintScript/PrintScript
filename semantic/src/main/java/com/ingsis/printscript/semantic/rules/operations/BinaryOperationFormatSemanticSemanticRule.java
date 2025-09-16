package com.ingsis.printscript.semantic.rules.operations;


import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.rules.type.ExpressionTypeGetter;
import com.ingsis.printscript.semantic.rules.type.ExpressionTypeGetterInterface;

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
            return new IncorrectResult<>(getLeftNodeResult.errorMessage());
        }
        ExpressionNode leftChild = getLeftNodeResult.result();
        String expectedType;
        if (leftChild instanceof LiteralNode literalLeftNode) {
            expectedType =  typeGetter.getType(literalLeftNode);
        } else if (leftChild instanceof IdentifierNode identifierLeftNode) {
            expectedType = typeGetter.getType(identifierLeftNode);
        } else {
            Result<String> checkResult = new OperationFormatSemanticRule().checkRules(leftChild);
            if (!checkResult.isSuccessful()) {
                return new IncorrectResult<>(checkResult.errorMessage());
            }
            expectedType = typeGetter.getType(leftChild);
        }

        Result<ExpressionNode> getRightNodeResult = binaryExpression.getRightChild();
        if (!getRightNodeResult.isSuccessful()) {
            return new IncorrectResult<>(getRightNodeResult.errorMessage());
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
            Result<String> checkResult = new OperationFormatSemanticRule().checkRules(rightChild);
            if (!checkResult.isSuccessful()) {
                return new IncorrectResult<>(checkResult.errorMessage());
            }
            if (!expectedType.equals(typeGetter.getType(rightChild))) {
                return new IncorrectResult<>("This node does not pass the check.");
            }
        }
        return new CorrectResult<String>("This node passes the check.");
    }
}
