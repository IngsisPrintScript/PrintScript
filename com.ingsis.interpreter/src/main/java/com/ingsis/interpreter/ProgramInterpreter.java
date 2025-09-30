/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.result.Result;

public sealed interface ProgramInterpreter permits DefaultProgramInterpreter {
    Result<String> interpret();
}
