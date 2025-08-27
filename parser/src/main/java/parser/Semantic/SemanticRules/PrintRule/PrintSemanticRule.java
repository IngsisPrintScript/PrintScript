package parser.Semantic.SemanticRules.PrintRule;


import common.Node;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import expression.literal.LiteralNode;
import parser.Semantic.SemanticRules.SemanticRulesInterface;
import statements.PrintStatementNode;

public class PrintSemanticRule implements SemanticRulesInterface {
    @Override
    public boolean match(Node operator) {
        return operator instanceof PrintStatementNode;
    }

    @Override
    public Result checkRules(Node leftLiteral, Node rightLiteral) {
        if (!(rightLiteral instanceof LiteralNode right)) {
            return new IncorrectResult("Print statement must be a literal");
        }
        return new CorrectResult<>(leftLiteral);
    }
}
