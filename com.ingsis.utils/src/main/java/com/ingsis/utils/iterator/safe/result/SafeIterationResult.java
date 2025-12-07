/*
 * My Project
 */

package com.ingsis.utils.iterator.safe.result;

import com.ingsis.utils.iterator.safe.SafeIterator;

public sealed interface SafeIterationResult<T>
        permits CorrectIterationResult, IncorrectIterationResult {
    Boolean isCorrect();

    SafeIterator<T> nextIterator();

    T iterationResult();

    String error();
}
