package analyzers.semantic;

import analyzers.Analyzer;
import analyzers.lexic.repositories.code.CodeRepositoryInterface;
import responses.Response;
import token.TokenInterface;

import java.util.List;

public record SemanticAnalyzer(List<TokenInterface> tokens, CodeRepositoryInterface repository) implements Analyzer {
    @Override
    public Response analyze() {
        Response response = repository.getCode();
        if (!response.isSuccessful()){
            return response;
        }
        return null;
    }
}
