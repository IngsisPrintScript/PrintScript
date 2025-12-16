/*
 * My Project
 */

package com.ingsis.utils.evalstate;

import com.ingsis.utils.value.Value;

public sealed interface EvalResult {
    public record CORRECT(Value value, EvalState evalState) implements EvalResult {}

    public record INCORRECT(String error, EvalState evalState) implements EvalResult {}
}
