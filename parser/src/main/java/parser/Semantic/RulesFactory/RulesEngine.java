package parser.Semantic.RulesFactory;

import common.Node;
import common.responses.CorrectResult;
import common.responses.Result;
import parser.Semantic.SemanticRules.SemanticRulesInterface;

import java.util.List;

public record RulesEngine(List<SemanticRulesInterface> semanticRulesInterface) {

    public Result checkRules(Node leftLiteral, Node rightLiteral, Node operator) {
        for (SemanticRulesInterface semanticRulesInterface : semanticRulesInterface) {
            if (semanticRulesInterface.match(operator)) {
                return semanticRulesInterface.checkRules(leftLiteral, rightLiteral);
            }
        }
        return new CorrectResult<>(null);
    }
}
