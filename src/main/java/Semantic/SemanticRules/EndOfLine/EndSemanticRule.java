package Semantic.SemanticRules.EndOfLine;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import common.visitor.VisitorInterface;

public class EndSemanticRule implements SemanticRulesInterface {
    @Override
    public boolean match(LiteralNode leftLiteralNode, LiteralNode rightLiteralNode, BinaryExpression operator) {
        return true;
    }

    @Override
    public Result checkRules(LiteralNode leftLiteral, LiteralNode rightLiteral, BinaryExpression operator) {
        if(match(leftLiteral, rightLiteral, operator)) {
           return new CorrectResult<>(null);
        }
        return new IncorrectResult("Cuac error");
    }
}
