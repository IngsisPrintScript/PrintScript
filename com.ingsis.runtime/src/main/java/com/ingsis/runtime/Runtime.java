/*
 * My Project
 */

package com.ingsis.runtime;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.Environment;

public sealed interface Runtime permits DefaultRuntime {
    Environment getCurrentEnvironment();

    Result<Environment> push();

    Result<Environment> pop();
}
