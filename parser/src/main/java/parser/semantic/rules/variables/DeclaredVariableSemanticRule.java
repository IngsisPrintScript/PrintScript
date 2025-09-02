package parser.semantic.rules.variables;

import common.Environment;
import common.EnvironmentInterface;
import common.Node;
import expression.identifier.IdentifierNode;
import factories.NodeFactory;
import parser.semantic.rules.SemanticRule;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

public record DeclaredVariableSemanticRule() implements SemanticRule {
    private static final Node template = new NodeFactory().createIdentifierNode("placeholder");
    @Override
    public boolean match(Node node) {
        return node.equals(template);
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (!(nodeToCheck instanceof IdentifierNode(String name))) {
            return new IncorrectResult<>("This rule does not apply to the received node");
        }
        EnvironmentInterface environment = Environment.getInstance();
        if (environment.variableIsDeclared(name)){
            return new CorrectResult<String>("Variable has already been declared.");
        } else {
            return new IncorrectResult<>("Variable has not been declared.");
        }
    }
}
