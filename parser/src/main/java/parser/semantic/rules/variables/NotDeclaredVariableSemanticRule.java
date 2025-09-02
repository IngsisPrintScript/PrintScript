package parser.semantic.rules.variables;

import nodes.common.Node;
import parser.semantic.rules.SemanticRule;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public record NotDeclaredVariableSemanticRule() implements SemanticRule {
    private static final SemanticRule OPPOSITE_SEMANTIC_RULE = new DeclaredVariableSemanticRule();
    @Override
    public boolean match(Node node) {
        return OPPOSITE_SEMANTIC_RULE.match(node);
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (OPPOSITE_SEMANTIC_RULE.checkRules(nodeToCheck).isSuccessful()){
            return new IncorrectResult<>("The variable is already declared.");
        } else {
            return new CorrectResult<String>("The variable is not declared.");
        }
    }
}
