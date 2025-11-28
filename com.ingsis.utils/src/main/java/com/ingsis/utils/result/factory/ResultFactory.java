/*
 * My Project
 */

package com.ingsis.result.factory;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;

public interface ResultFactory {
    <T> IncorrectResult<T> createIncorrectResult(String errorMessage);

    <T> IncorrectResult<T> cloneIncorrectResult(Result<?> originalIncorrectResult);

    <T> CorrectResult<T> createCorrectResult(T result);
}
