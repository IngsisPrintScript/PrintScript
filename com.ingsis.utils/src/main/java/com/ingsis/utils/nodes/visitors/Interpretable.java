/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors;

import com.ingsis.utils.result.Result;

public interface Interpretable {
    Result<String> acceptInterpreter(Interpreter interpreter);
}
