package parser.Semantic.SemanticRules.RulesCheck;


import common.Node;
import responses.CorrectResult;
import responses.Result;
import parser.Semantic.RulesFactory.RulesEngine;

public record CheckSemanticRules(RulesEngine semanticRules) {

    public Result checkSemanticRules(Result left, Result right, Node operator) {
        Object leftLiteral = ((CorrectResult<?>) left).newObject();
        Object rightLiteral = ((CorrectResult<?>) right).newObject();
        return semanticRules.checkRules((Node) leftLiteral, (Node) rightLiteral, operator);
    }
}
