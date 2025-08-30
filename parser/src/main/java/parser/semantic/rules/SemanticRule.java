package parser.semantic.rules;


import common.Node;
import responses.Result;

public interface SemanticRule {

    boolean match(Node node);

    Result<String> checkRules(Node nodeToCheck);
}
