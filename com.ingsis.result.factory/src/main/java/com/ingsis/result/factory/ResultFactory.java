/*
 * My Project
 */

package com.ingsis.result.factory;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;

public interface ResultFactory {
    <T> IncorrectResult<T> createIncorrectResult(String errorMessage);

    <T> IncorrectResult<T> cloneIncorrectResult(IncorrectResult<?> originalIncorrectResult);

    <T> CorrectResult<T> createCorrectResult(T result);
}
