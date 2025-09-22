/*
 * My Project
 */

package com.ingsis.result;

public record IncorrectResult<T>(String error) implements Result<T> {
    public IncorrectResult(Result<?> incorrectResult) {
        this(incorrectResult.error());
    }

    @Override
    public T result() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean isCorrect() {
        return false;
    }
}
