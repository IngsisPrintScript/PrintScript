/*
 * My Project
 */

package com.ingsis.result.factory;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;

public class DefaultResultFactory implements ResultFactory {

    @Override
    public <T> IncorrectResult<T> createIncorrectResult(String errorMessage) {
        return new IncorrectResult<>(errorMessage);
    }

    @Override
    public <T> IncorrectResult<T> cloneIncorrectResult(IncorrectResult<?> originalIncorrectResult) {
        return new IncorrectResult<>(originalIncorrectResult.error());
    }

    @Override
    public <T> CorrectResult<T> createCorrectResult(T result) {
        return new CorrectResult<>(result);
    }
}
