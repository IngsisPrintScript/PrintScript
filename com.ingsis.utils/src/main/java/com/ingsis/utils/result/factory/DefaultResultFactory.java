/*
 * My Project
 */

package com.ingsis.utils.result.factory; /*
                                          * My Project
                                          */

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public class DefaultResultFactory implements ResultFactory {

    @Override
    public <T> IncorrectResult<T> createIncorrectResult(String errorMessage) {
        return new IncorrectResult<>(errorMessage);
    }

    @Override
    public <T> IncorrectResult<T> cloneIncorrectResult(Result<?> originalIncorrectResult) {
        return new IncorrectResult<>(originalIncorrectResult.error());
    }

    @Override
    public <T> CorrectResult<T> createCorrectResult(T result) {
        return new CorrectResult<>(result);
    }
}
