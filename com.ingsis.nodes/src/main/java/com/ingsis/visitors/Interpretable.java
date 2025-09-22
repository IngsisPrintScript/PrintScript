/*
 * My Project
 */

package com.ingsis.visitors;

import com.ingsis.result.Result;

public interface Interpretable {
    Result<String> acceptInterpreter(Interpreter interpreter);
}
