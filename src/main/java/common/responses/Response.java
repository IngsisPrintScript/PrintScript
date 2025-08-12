package common.responses;

public sealed interface Response permits CorrectResponse, IncorrectResponse {
    Boolean isSuccessful();
}
