/*
 * My Project
 */

package com.ingsis.runtime;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.FunctionEntry;

public sealed interface Runtime permits DefaultRuntime {
  Environment getCurrentEnvironment();

  Result<Environment> push();

  Result<Environment> pushClosure(FunctionEntry functionEntry);

  Result<Environment> pop();
}
