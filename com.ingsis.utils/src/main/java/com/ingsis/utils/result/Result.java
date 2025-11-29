/*
 * My Project
 */

package com.ingsis.utils.result; /*
                                  * My Project
                                  */

public sealed interface Result<T> permits CorrectResult, IncorrectResult {
    T result();

    String error();

    Boolean isCorrect();
}
