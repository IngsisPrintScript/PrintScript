/*
 * My Project
 */

package com.ingsis.utils.result.factory; /*
                                          * My Project
                                          */

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public interface ResultFactory {
    <T> IncorrectResult<T> createIncorrectResult(String errorMessage);

    <T> IncorrectResult<T> cloneIncorrectResult(Result<?> originalIncorrectResult);

    <T> CorrectResult<T> createCorrectResult(T result);
}
