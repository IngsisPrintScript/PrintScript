/*
 * My Project
 */

package com.ingsis.utils.nodes.visitors; /*
                                          * My Project
                                          */

import com.ingsis.utils.result.Result;

public interface Interpretable {
    Result<String> acceptInterpreter(Interpreter interpreter);
}
