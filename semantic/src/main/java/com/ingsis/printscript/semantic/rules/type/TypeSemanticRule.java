/*
 * My Project
 */

package com.ingsis.printscript.semantic.rules.type;

import com.ingsis.printscript.astnodes.Node;
import com.ingsis.printscript.astnodes.expression.function.CallFunctionNode;
import com.ingsis.printscript.astnodes.expression.identifier.IdentifierNode;
import com.ingsis.printscript.astnodes.expression.literal.LiteralNode;
import com.ingsis.printscript.astnodes.factories.NodeFactory;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.runtime.Runtime;
import com.ingsis.printscript.semantic.rules.SemanticRule;

import java.util.HashMap;
import java.util.Map;

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
                Result<String> getTypeResult =
                        Runtime.getInstance().currentEnv().getVariableType(name);
                if (!getTypeResult.isSuccessful()) return getTypeResult;
                String type = getTypeResult.result();
                if (type.equals(expectedType)) {
                    return new CorrectResult<>("Type is equal to the expected type");
                } else {
                    return new IncorrectResult<>("Type is not equal to the expected type");
                }
            }
            if (nodeToCheck instanceof CallFunctionNode callFunctionNode) {
                Class<?> type =
                        Runtime.getInstance().currentEnv()
                                .getFunction(callFunctionNode.identifier().name()).result().returnType();
                if (getType(type).equals(expectedType) || getType(type).equals("Any")) {
                    return new CorrectResult<>("Type is equal to the expected type");
                };
            }
            return new IncorrectResult<>("This rule does not apply to the received type of node");
        }
        if (expectedType.equals(typeGetter.getType(nodeToCheck))) {
            return new CorrectResult<String>("Type is equal to the expected type");
        } else {
            return new IncorrectResult<>("Type is not equal to the expected type");
        }
    }

    private String getType(Class<?> clazz){
        Map<Class<?>, String> dict = new HashMap<Class<?>, String>();
        dict.put(String.class, "String");
        dict.put(Number.class, "Number");
        dict.put(Boolean.class, "Boolean");
        dict.put(Object.class, "Any");
        if (dict.containsKey(clazz)) {
            return dict.get(clazz);
        }
        return  "Unknown";
    }
}
