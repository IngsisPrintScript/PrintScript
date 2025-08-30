package parser.semantic.rules.type;

import common.Environment;
import common.Node;
import expression.identifier.IdentifierNode;
import expression.binary.BinaryExpression;
import expression.literal.LiteralNode;
import responses.CorrectResult;
import responses.Result;

public record ExpressionTypeGetter() implements ExpressionTypeGetterInterface {
    @Override
    public String getType(Node node) {
        if (node instanceof IdentifierNode identifierNode){
            Result getIdType = Environment.getInstance().getIdType(identifierNode.value());
            if (!getIdType.isSuccessful()) return "UnknownType";
            return ((CorrectResult<String>) getIdType).result();
        }
        if (node instanceof LiteralNode literalNode) {
            String literal = literalNode.value();
            if (literal.startsWith("\"") && literal.endsWith("\"")) {
                return "String";
            } else if (literal.startsWith("'") && literal.endsWith("'")) {
                return "String";
            }
            boolean isNumber = true;
            for (char c: literal.toCharArray()){
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
            Result getLeftChildResult = binaryExpression.leftChild();
            if (!getLeftChildResult.isSuccessful()) return "UnknownType";
            Node leftChild = ((CorrectResult<Node>) getLeftChildResult).result();
            return this.getType(leftChild);
        }
    }
}
