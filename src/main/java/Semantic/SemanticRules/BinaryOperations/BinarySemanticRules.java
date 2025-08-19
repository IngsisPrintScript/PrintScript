package Semantic.SemanticRules.BinaryOperations;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.responses.IncorrectResult;
import common.responses.Result;

public record BinarySemanticRules(SemanticRulesInterface nextSemanticRule) implements SemanticRulesInterface {
    @Override
    public boolean match(LiteralNode leftLiteralNode, LiteralNode rightLiteralNode, BinaryExpression operator) {
        if(!(operator instanceof AdditionNode)){
            return false;
        }
        try{
            Double.parseDouble(leftLiteralNode.value());
            Double.parseDouble(rightLiteralNode.value());
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    @Override
    public Result checkRules(LiteralNode leftLiteral, LiteralNode rightLiteral, BinaryExpression operator) {
        if(match(leftLiteral,rightLiteral,operator)){
            return nextSemanticRule.checkRules(leftLiteral,rightLiteral,operator);
        }
        return new IncorrectResult("Cant operate with a String");
    }
}
