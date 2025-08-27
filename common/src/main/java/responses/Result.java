package responses;

public sealed interface Result permits CorrectResult, IncorrectResult {
    Boolean isSuccessful();
}
