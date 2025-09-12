package com.ingsis.printscript.results;

public sealed interface Result<T> permits CorrectResult, IncorrectResult {
    Boolean isSuccessful();
    T result();
    String errorMessage();
}
