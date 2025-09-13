/*
 * My Project
 */

package com.ingsis.printscript.results;

public record CorrectResult<T>(T result) implements Result<T> {
    @Override
    public Boolean isSuccessful() {
        return true;
    }

    @Override
    public String errorMessage() {
        throw new UnsupportedOperationException("Correct Result does not support errorMessage");
    }
}
