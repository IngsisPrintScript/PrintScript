package parser.SemanticRules.IdentifierRules;

import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import common.responses.Response;

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
