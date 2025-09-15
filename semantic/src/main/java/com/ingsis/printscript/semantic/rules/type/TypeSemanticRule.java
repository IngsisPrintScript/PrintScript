/*
 * My Project
 */

package com.ingsis.printscript.semantic.rules.type;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.semantic.rules.SemanticRule;

public record TypeSemanticRule(String expectedType, ExpressionTypeGetterInterface typeGetter)
        implements SemanticRule {
    private static final Node TEMPLATE = new NodeFactory().createTypeNode("placeholder");

    public TypeSemanticRule(String expectedType) {
        this(expectedType, new ExpressionTypeGetter());
    }

    @Override
    public boolean match(Node node) {
        return node.equals(TEMPLATE);
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (!(nodeToCheck instanceof LiteralNode)) {
            if (nodeToCheck instanceof IdentifierNode(String name)) {
                Result<String> getTypeResult = Runtime.getInstance().currentEnv().getIdType(name);
                if (!getTypeResult.isSuccessful()) return getTypeResult;
                String type = getTypeResult.result();
                if (type.equals(expectedType)) {
                    return new CorrectResult<>("Type is equal to the expected type");
                } else {
                    return new IncorrectResult<>("Type is not equal to the expected type");
                }
            }
            return new IncorrectResult<>("This rule does not apply to the received type of node");
        }
        if (expectedType.equals(typeGetter.getType(nodeToCheck))) {
            return new CorrectResult<String>("Type is equal to the expected type");
        } else {
            return new IncorrectResult<>("Type is not equal to the expected type");
        }
    }
}
