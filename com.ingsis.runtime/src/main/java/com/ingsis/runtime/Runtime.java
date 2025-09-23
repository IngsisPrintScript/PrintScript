/*
 * My Project
 */

package com.ingsis.runtime;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.Environment;

public interface Runtime {
    Environment getCurrentEnvironment();

    Result<Environment> push(Environment environment);

    Result<Environment> pop();
}
