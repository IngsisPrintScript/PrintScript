package com.ingsis.printscript.semantic.rules;


import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.Result;

public interface SemanticRule {

    boolean match(Node node);

    Result<String> checkRules(Node nodeToCheck);
}
