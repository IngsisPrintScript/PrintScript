/*
 * My Project
 */

package com.ingsis.printscript.semantic.rules.variables;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.runtime.environment.EnvironmentInterface;
import com.ingsis.printscript.semantic.rules.SemanticRule;

public record DeclaredVariableSemanticRule() implements SemanticRule {
    private static final Node TEMPLATE = new NodeFactory().createIdentifierNode("placeholder");

    @Override
    public boolean match(Node node) {
        return node.equals(TEMPLATE);
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (!(nodeToCheck instanceof IdentifierNode(String name))) {
            return new IncorrectResult<>("This rule does not apply to the received node");
        }
        EnvironmentInterface environment = Runtime.getInstance().currentEnv();
        if (environment.variableIsDeclared(name)) {
            return new CorrectResult<String>("Variable has already been declared.");
        } else {
            return new IncorrectResult<>("Variable has not been declared.");
        }
    }
}
