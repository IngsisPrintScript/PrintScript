package parser.SemanticRules;

import common.responses.CorrectResponse;
import common.responses.Response;
import lexer.repositories.code.CodeRepositoryInterface;
import parser.SemanticRulesInterface.SemanticRulesInterface;

import java.util.List;

public record RulesRepository(List<SemanticRulesInterface> semanticRules) implements CodeRepositoryInterface {
    @Override
    public Response getCode() {
        return new CorrectResponse<List<SemanticRulesInterface>>(semanticRules);
    }
}
