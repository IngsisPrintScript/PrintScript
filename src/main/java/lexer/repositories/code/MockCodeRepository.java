package lexer.repositories.code;

import common.responses.CorrectResponse;
import common.responses.Response;

public record MockCodeRepository(String mockCode) implements CodeRepositoryInterface {
    @Override
    public Response getCode() {
        return new CorrectResponse<String>(mockCode());
    }
}
