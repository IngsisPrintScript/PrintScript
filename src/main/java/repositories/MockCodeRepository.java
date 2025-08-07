package repositories;

import responses.CorrectResponse;
import responses.Response;

public record MockCodeRepository(String mockCode) implements CodeRepositoryInterface {
    @Override
    public Response getCode() {
        return new CorrectResponse<String>(mockCode());
    }
}
