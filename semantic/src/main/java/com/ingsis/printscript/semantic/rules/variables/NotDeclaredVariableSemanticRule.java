/*
 * My Project
 */

package com.ingsis.printscript.semantic.rules.variables;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.rules.SemanticRule;

public record NotDeclaredVariableSemanticRule() implements SemanticRule {
    private static final SemanticRule OPPOSITE_SEMANTIC_RULE = new DeclaredVariableSemanticRule();

    @Override
    public boolean match(Node node) {
        return OPPOSITE_SEMANTIC_RULE.match(node);
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (OPPOSITE_SEMANTIC_RULE.checkRules(nodeToCheck).isSuccessful()) {
            return new IncorrectResult<>("The variable is already declared.");
        } else {
            return new CorrectResult<String>("The variable is not declared.");
        }
    }
}
