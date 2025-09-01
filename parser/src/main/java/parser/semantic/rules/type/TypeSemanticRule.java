package parser.semantic.rules.type;

import common.Node;
import expression.literal.LiteralNode;
import factories.NodeFactory;
import parser.semantic.rules.SemanticRule;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;

public record TypeSemanticRule(String expectedType, ExpressionTypeGetterInterface typeGetter) implements SemanticRule {
    private static final Node template = new NodeFactory().createTypeNode("placeholder");

    public TypeSemanticRule(String expectedType) {
        this(expectedType, new ExpressionTypeGetter());
    }

    @Override
    public boolean match(Node node) {
        return node.equals(template);
    }

    @Override
    public Result<String> checkRules(Node nodeToCheck) {
        if (!(nodeToCheck instanceof LiteralNode literalNode)) {
            return new IncorrectResult<>("This rule does not apply to the received type of node");
        }
        if (expectedType.equals(typeGetter.getType(nodeToCheck))){
            return new CorrectResult<String>("Literal type is equal to the expected type");
        } else {
            return new IncorrectResult<>("Literal type is not equal to the expected type");
        }
    }
}
