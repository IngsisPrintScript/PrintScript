package parser.semantic.rules;

import nodes.common.Node;
import results.Result;

public interface SemanticRule {

  boolean match(Node node);

  Result<String> checkRules(Node nodeToCheck);
}
