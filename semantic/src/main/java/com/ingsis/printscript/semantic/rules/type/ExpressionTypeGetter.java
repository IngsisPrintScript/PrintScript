package com.ingsis.printscript.semantic.rules.type;


import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.ExpressionNode;
import com.ingsis.printscript.astnodes.expression.binary.BinaryExpression;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.environment.Environment;
import com.ingsis.printscript.results.Result;

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

            try{
                Double.parseDouble(value);
            } catch(Exception ignore) {
                isNumber = false;
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
