/*
 * My Project
 */

package com.ingsis.runtime;

import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public sealed interface Runtime permits DefaultRuntime {
    Environment getCurrentEnvironment();

    Result<Environment> push();

    Result<Environment> pushClosure(FunctionEntry functionEntry);

    Result<Environment> pop();

    void setExecutionError(IncorrectResult<?> result);

    IncorrectResult<?> getExecutionError();
}
