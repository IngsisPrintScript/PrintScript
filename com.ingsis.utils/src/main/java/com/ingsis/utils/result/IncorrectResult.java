/*
 * My Project
 */

package com.ingsis.utils.result; /*
                                  * My Project
                                  */

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
