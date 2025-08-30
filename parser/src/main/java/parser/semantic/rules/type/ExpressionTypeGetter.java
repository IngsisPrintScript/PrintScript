package parser.semantic.rules.type;

import common.Environment;
import common.Node;
import expression.ExpressionNode;
import expression.identifier.IdentifierNode;
import expression.binary.BinaryExpression;
import expression.literal.LiteralNode;
import responses.CorrectResult;
import responses.Result;

public record ExpressionTypeGetter() implements ExpressionTypeGetterInterface {
    @Override
    public String getType(Node node) {
        if (node instanceof IdentifierNode(String name)){
            Result<String> getIdType = Environment.getInstance().getIdType(name);
            if (!getIdType.isSuccessful()) return "UnknownType";
            return getIdType.result();
        }
        if (node instanceof LiteralNode(String value)) {
            if (value.startsWith("\"") && value.endsWith("\"")) {
                return "String";
            } else if (value.startsWith("'") && value.endsWith("'")) {
                return "String";
            }
            boolean isNumber = true;
            for (char c: value.toCharArray()){
                if (!Character.isDigit(c)){
                    isNumber = false;
                }
            }
            if (isNumber){
                return "Number";
            } else {
                return "UnknownType";
            }
        } else {
            if (!(node instanceof BinaryExpression binaryExpression)) return "UnknownType";
            Result<ExpressionNode> getLeftChildResult = binaryExpression.getLeftChild();
            if (!getLeftChildResult.isSuccessful()) return "UnknownType";
            ExpressionNode leftChild = getLeftChildResult.result();
            return this.getType(leftChild);
        }
    }
}
