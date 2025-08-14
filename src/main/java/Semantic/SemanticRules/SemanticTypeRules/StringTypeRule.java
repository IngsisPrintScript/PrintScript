package Semantic.SemanticRules.SemanticTypeRules;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.Node;
import common.nodes.expression.binary.BinaryExpression;
import common.responses.Result;

import java.util.List;
import java.util.function.BinaryOperator;

public class StringTypeRule implements SemanticRulesInterface {

    @Override
    public boolean match(Node leftLiteralNode, Node rightLiteralNode, BinaryExpression operator) {
        return false;
    }

    @Override
    public Result checkRules(Node leftLiteral, Node rightLiteral, BinaryExpression operator) {
        return null;
    }
}
