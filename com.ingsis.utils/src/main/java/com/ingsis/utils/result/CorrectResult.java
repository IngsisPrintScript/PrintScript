/*
 * My Project
 */

package com.ingsis.result;

public record CorrectResult<T>(T result) implements Result<T> {
    @Override
    public String error() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean isCorrect() {
        return true;
    }
}
