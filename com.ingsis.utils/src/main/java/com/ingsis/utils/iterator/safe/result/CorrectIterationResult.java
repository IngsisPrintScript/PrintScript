/*
 * My Project
 */

package com.ingsis.utils.iterator.safe.result;

import com.ingsis.utils.iterator.safe.SafeIterator;

public final class CorrectIterationResult<T> implements SafeIterationResult<T> {

    private final SafeIterator<T> nextIterator;
    private final T previousIterationResult;

    private CorrectIterationResult(SafeIterator<T> nextIterator, T previousIterationResult) {
        this.nextIterator = nextIterator;
        this.previousIterationResult = previousIterationResult;
    }

    @Override
    public SafeIterator<T> nextIterator() {
        return nextIterator;
    }

    @Override
    public T iterationResult() {
        return previousIterationResult;
    }

    static <T, I extends SafeIterator<T>> CorrectIterationResult<T> build(
            T iterationResult, I nextIterator) {
        return new CorrectIterationResult<>(nextIterator, iterationResult);
    }

    @Override
    public Boolean isCorrect() {
        return true;
    }

    @Override
    public String error() {
        throw new UnsupportedOperationException("Correct result does not have error.");
    }
}
