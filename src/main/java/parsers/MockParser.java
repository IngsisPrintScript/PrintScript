package parsers;

import repositories.CodeRepositoryInterface;
import responses.CorrectResponse;
import responses.IncorrectResponse;
import responses.Response;

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
