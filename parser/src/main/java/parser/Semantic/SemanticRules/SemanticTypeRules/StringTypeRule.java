package parser.Semantic.SemanticRules.SemanticTypeRules;


import common.Node;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;
import declaration.TypeNode;
import expression.literal.LiteralNode;
import parser.Semantic.SemanticRules.SemanticRulesInterface;
import statements.LetStatementNode;

public class StringTypeRule implements SemanticRulesInterface {

    @Override
    public boolean match(Node operator) {
        return operator instanceof LetStatementNode;
    }

    @Override
    public Result checkRules(Node leftLiteral, Node rightLiteral) {
        TypeNode leftType = (TypeNode) leftLiteral;
        String rightNode = ((LiteralNode) rightLiteral).value();
        if ("String".equalsIgnoreCase(leftType.value())) {
            try {
                Double.parseDouble(rightNode);
                return new IncorrectResult("Type mismatch: expected Number but got " + rightNode);

            } catch (NumberFormatException e) {
                return new CorrectResult<>(rightNode);
            }
        }
        return new IncorrectResult("Unsupported type in NumericTypeRules: " + leftType.value());
    }
}
