package Semantic.SemanticRules.SemanticTypeRules;

import Semantic.SemanticRules.SemanticRulesInterface;
import Semantic.SemanticVisitor.SemanticVisitor;
import common.nodes.Node;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.util.List;
import java.util.function.BinaryOperator;

public record StringTypeRule(SemanticRulesInterface nextSemanticVisitor) implements SemanticRulesInterface {

    @Override
    public boolean match(LiteralNode leftLiteralNode, LiteralNode rightLiteralNode, BinaryExpression operator) {
        if(operator != null && !leftLiteralNode.value().equals("String")){
            return false;
        }try {
            Double.parseDouble(leftLiteralNode.value());
            Double.parseDouble(rightLiteralNode.value());
        }catch (NumberFormatException e){
            return true;
        }
        return false;
    }

    @Override
    public Result checkRules(LiteralNode leftLiteral, LiteralNode rightLiteral, BinaryExpression operator){
        if(match(leftLiteral,rightLiteral,operator)){
            return nextSemanticVisitor.checkRules(leftLiteral,rightLiteral,operator);
        }
        return new IncorrectResult("Let statement has no declaration.");
    }
}
