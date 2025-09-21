package com.ingsis.result;

public sealed interface Result<T> permits CorrectResult, IncorrectResult {
    T result();
    String error();
}
