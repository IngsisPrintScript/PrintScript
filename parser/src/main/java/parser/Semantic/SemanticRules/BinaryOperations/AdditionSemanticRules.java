package parser.Semantic.SemanticRules.BinaryOperations;


import common.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import expression.binary.AdditionNode;
import expression.literal.LiteralNode;
import parser.Semantic.SemanticRules.SemanticRulesInterface;

public class AdditionSemanticRules implements SemanticRulesInterface {

    @Override
    public boolean match(Node operator) {
        return operator instanceof AdditionNode;
    }

    @Override
    public Result checkRules(Node leftLiteral, Node rightLiteral) {
        if (!(leftLiteral instanceof LiteralNode left) || !(rightLiteral instanceof LiteralNode right)) {
            return new IncorrectResult("Addition operation must be between two literals");
        }
        return new CorrectResult<>(left.value() + right.value());
    }
}
