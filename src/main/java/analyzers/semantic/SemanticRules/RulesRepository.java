package analyzers.semantic.SemanticRules;

import analyzers.lexic.repositories.code.CodeRepositoryInterface;
import analyzers.semantic.SemanticRulesInterface.SemanticRulesInterface;
import responses.CorrectResponse;
import responses.Response;

import java.util.List;

public record RulesRepository(List<SemanticRulesInterface> semanticRules) implements CodeRepositoryInterface {
    @Override
    public Response getCode() {
        return new CorrectResponse<List<SemanticRulesInterface>>(semanticRules);
    }
}
