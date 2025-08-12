package parser;

import common.responses.Response;
import common.token.TokenInterface;
import

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
