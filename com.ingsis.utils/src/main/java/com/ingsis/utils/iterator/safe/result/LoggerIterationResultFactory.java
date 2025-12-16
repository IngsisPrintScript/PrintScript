/*
 * My Project
 */

package com.ingsis.utils.iterator.safe.result;

import com.ingsis.utils.iterator.safe.SafeIterator;

public class LoggerIterationResultFactory implements IterationResultFactory {
    private final IterationResultFactory baseFactory;

    public LoggerIterationResultFactory(IterationResultFactory baseFactory) {
        this.baseFactory = baseFactory;
    }

    @Override
    public <T> SafeIterationResult<T> createCorrectResult(
            T iterationResult, SafeIterator<T> nextIterator) {
        return baseFactory.createCorrectResult(iterationResult, nextIterator);
    }

    @Override
    public <T> SafeIterationResult<T> createIncorrectResult(String error) {
        return baseFactory.createIncorrectResult(error);
    }

    @Override
    public <T> SafeIterationResult<T> cloneIncorrectResult(
            SafeIterationResult<?> originalIncorrectResult) {
        return baseFactory.createIncorrectResult(originalIncorrectResult.error());
    }
}
