package Semantic.SemanticRules;

import common.nodes.Node;
import common.responses.Result;

public interface SemanticRulesInterface {

    boolean match(Node link);
    Result checkRules(Node link);
}
