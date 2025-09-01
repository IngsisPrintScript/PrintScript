package responses;

public sealed interface Result<T> permits CorrectResult, IncorrectResult {
    Boolean isSuccessful();
    T result();
}
