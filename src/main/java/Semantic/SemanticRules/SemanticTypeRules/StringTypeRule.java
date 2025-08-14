package Semantic.SemanticRules.SemanticTypeRules;

import Semantic.SemanticRules.SemanticRulesInterface;
import common.nodes.Node;
import common.responses.Result;

public class StringTypeRule implements SemanticRulesInterface {
    @Override
    public boolean match(Node link) {
        return false;
    }

    @Override
    public Result checkRules(Node link) {
        return null;
    }
}
