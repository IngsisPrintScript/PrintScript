package Semantic.SemanticRules.BinaryOperations;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.Node;
import common.nodes.expression.binary.AdditionNode;
import common.nodes.expression.literal.LiteralNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.util.List;

public class AdditionSemanticRules implements SemanticRulesInterface {

    @Override
    public boolean match(Node operator) {
        return operator instanceof AdditionNode;
    }

    @Override
    public Result checkRules(Node leftLiteral, Node rightLiteral) {
        double leftValue, rightValue;
        try{
            leftValue = Double.parseDouble(((LiteralNode) leftLiteral).value());
            rightValue = Double.parseDouble(((LiteralNode) rightLiteral).value());
        }catch(NumberFormatException e){
            return new IncorrectResult("Operands must be numeric literals");
        }
        return new CorrectResult<>(new LiteralNode(String.valueOf(leftValue+rightValue)));
    }
}
