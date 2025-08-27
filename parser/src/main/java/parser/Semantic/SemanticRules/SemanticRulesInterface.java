package parser.Semantic.SemanticRules;


import common.Node;
import responses.Result;

public interface SemanticRulesInterface {

    boolean match(Node operator);

    Result checkRules(Node leftLiteral, Node rightLiteral);
}
