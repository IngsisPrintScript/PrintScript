package analyzers.semantic.SemanticRules.IdentifierRules;

import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import nodes.expressions.leaf.IdentifierExpressionNode;
import nodes.expressions.leaf.LiteralExpressionNode;
import nodes.expressions.leaf.TypeExpressionNode;
import responses.Response;

import java.util.List;

public record AlreadyDefinedAVariableRule(SemanticRulesInterface nextSemanticRule) implements SemanticRulesInterface {
    @Override
    public boolean match(Object childrenResponse, String operationSymbol, String operationName) {
        return false;
    }

    @Override
    public Response getResponse(Object childrenResponse, String operationSymbol, String operationName) {
        return null;
    }
}
