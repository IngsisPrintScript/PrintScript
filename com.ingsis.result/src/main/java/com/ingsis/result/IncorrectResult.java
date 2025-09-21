package com.ingsis.result;

public record IncorrectResult<T>(String error) implements Result<T> {
    public IncorrectResult(IncorrectResult<T> incorrectResult){
        this(incorrectResult.error());
    }

    @Override
    public T result() {
        throw new UnsupportedOperationException();
    }
}
