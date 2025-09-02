package parser.semantic.rules;


import common.Node;
import results.Result;

public interface SemanticRule {

    boolean match(Node node);

    Result<String> checkRules(Node nodeToCheck);
}
