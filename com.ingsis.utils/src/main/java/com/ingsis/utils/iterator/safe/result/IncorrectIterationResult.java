/*
 * My Project
 */

package com.ingsis.utils.iterator.safe.result;

import com.ingsis.utils.iterator.safe.SafeIterator;

public final class IncorrectIterationResult<T> implements SafeIterationResult<T> {
    private final String error;

    private IncorrectIterationResult(String error) {
        this.error = error;
    }

    public String error() {
        return this.error;
    }

    static <T, I extends SafeIterator<T>> IncorrectIterationResult<T> build(String error) {
        return new IncorrectIterationResult<>(error);
    }

    @Override
    public Boolean isCorrect() {
        return false;
    }

    @Override
    public SafeIterator<T> nextIterator() {
        throw new UnsupportedOperationException("Incorrect result has no next iterator.");
    }

    @Override
    public String toString() {
        return "ERROR: " + error();
    }

    @Override
    public T iterationResult() {
        throw new UnsupportedOperationException("Incorrect result has no iteration result.");
    }
}
