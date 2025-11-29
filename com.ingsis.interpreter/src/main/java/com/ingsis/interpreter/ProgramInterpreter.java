/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.utils.result.Result;

public sealed interface ProgramInterpreter permits DefaultProgramInterpreter {
    Result<String> interpret();
}
