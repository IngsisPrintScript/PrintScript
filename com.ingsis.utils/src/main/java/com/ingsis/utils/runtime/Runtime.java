package com.ingsis.utils.runtime;

/*
 * My Project
 */

import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;

public sealed interface Runtime permits DefaultRuntime {
  Environment getCurrentEnvironment();

  Result<Environment> push();

  Result<Environment> pushClosure(FunctionEntry functionEntry);

  Result<Environment> pop();

  void setExecutionError(IncorrectResult<?> result);

  IncorrectResult<?> getExecutionError();
}
