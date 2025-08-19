package Semantic.SemanticRules.SemanticTypeRules;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.responses.IncorrectResult;
import common.responses.Result;

public record NumericTypeRules(SemanticRulesInterface nextSemanticVisitor) implements SemanticRulesInterface {
    @Override
    public boolean match(LiteralNode leftLiteralNode, LiteralNode rightLiteralNode, BinaryExpression operator) {
        if(operator != null){
            return false;
        }
        if(!leftLiteralNode.value().equals("Number")){
            return false;
        }
        try {
            Double.parseDouble(leftLiteralNode.value());
            Double.parseDouble(rightLiteralNode.value());
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    @Override
    public Result checkRules(LiteralNode leftLiteral, LiteralNode rightLiteral, BinaryExpression operator) {
        if(match(leftLiteral,rightLiteral,operator)){
            return nextSemanticVisitor.checkRules(leftLiteral,rightLiteral,operator);
        }
        return new IncorrectResult("Let statement has no declaration.");
    }
}
