package Semantic.SemanticRules.PrintRule;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.Node;
import common.nodes.declaration.IdentifierNode;
import common.nodes.expression.binary.BinaryExpression;
import common.nodes.expression.literal.LiteralNode;
import common.nodes.statements.PrintStatementNode;
import common.responses.CorrectResult;
import common.responses.IncorrectResult;
import common.responses.Result;

import java.util.List;

public class PrintSemanticRule implements SemanticRulesInterface {
    @Override
    public boolean match(Node operator) {
        return operator instanceof PrintStatementNode;
    }

    @Override
    public Result checkRules(Node leftLiteral, Node rightLiteral) {
        if(!(rightLiteral instanceof LiteralNode right)){
            return new IncorrectResult("Print statement must be a literal");
        }
        return new CorrectResult<>(leftLiteral);
    }
}
