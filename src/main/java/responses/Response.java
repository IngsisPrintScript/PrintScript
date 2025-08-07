package responses;

public sealed interface Response<T> permits CorrectResponse, IncorrectResponse {
    Boolean isSuccessful();
    T getResponse();
}
