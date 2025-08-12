package lexer.tokenizers.parsers;

import common.responses.CorrectResponse;
import common.responses.IncorrectResponse;
import common.responses.Response;
import lexer.repositories.code.CodeRepositoryInterface;

import java.util.List;

public record MockParser(CodeRepositoryInterface repository) implements ParserInterface {
    @Override
    public Response parse() {
        Response repositoryResponse = repository.getCode();
        if (!repositoryResponse.isSuccessful()) {
            return repositoryResponse;
        }
        Object object = ((CorrectResponse<?>) repositoryResponse).newObject();
        if (object instanceof String code) {
            List<String> words = List.of(code.split(" "));
            return new CorrectResponse<List<String>>(words);
        } else {
            return new IncorrectResponse("Repository returned something which was not a string");
        }
    }
}
