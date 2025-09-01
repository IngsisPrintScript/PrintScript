package parser.semantic.rules.type;

import common.Environment;
import common.Node;
import expression.identifier.IdentifierNode;
import expression.literal.LiteralNode;
import factories.NodeFactory;
import parser.semantic.rules.SemanticRule;
import results.CorrectResult;
import results.IncorrectResult;
import results.Result;

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
        if (!(nodeToCheck instanceof LiteralNode)) {
            if (nodeToCheck instanceof IdentifierNode(String name)) {
                Result<String> getTypeResult = Environment.getInstance().getIdType(name);
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
        if (expectedType.equals(typeGetter.getType(nodeToCheck))){
            return new CorrectResult<String>("Type is equal to the expected type");
        } else {
            return new IncorrectResult<>("Type is not equal to the expected type");
        }
    }
}
