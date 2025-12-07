package com.ingsis.utils.runtime;

/*
 * My Project
 */

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.runtime.environment.Environment;
import com.ingsis.utils.runtime.environment.entries.FunctionEntry;
import com.ingsis.utils.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.utils.runtime.environment.factories.DefaultEnvironmentFactory;
import com.ingsis.utils.runtime.environment.factories.EnvironmentFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public final class DefaultRuntime implements Runtime {
  private final Deque<Environment> environments;
  private IncorrectResult<?> executionError;
  private static final EnvironmentFactory ENVIRONMENT_FACTORY = new DefaultEnvironmentFactory(
      new DefaultEntryFactory());

  private DefaultRuntime() {
    environments = new ArrayDeque<>();
    environments.push(ENVIRONMENT_FACTORY.createGlobalEnvironment());
  }

  private static class Holder {
    private static final DefaultRuntime INSTANCE = new DefaultRuntime();
  }

  public static DefaultRuntime getInstance() {
    return Holder.INSTANCE;
  }

  @Override
  public Environment getCurrentEnvironment() {
    return environments.peek();
  }

  @Override
  public Result<Environment> push() {
    Environment newLocalEnvironment = ENVIRONMENT_FACTORY.createLocalEnvironment(getCurrentEnvironment());
    environments.push(newLocalEnvironment);
    return new CorrectResult<>(newLocalEnvironment);
  }

  @Override
  public Result<Environment> pop() {
    if (environments.size() <= 1) {
      return new IncorrectResult<>("Cannot pop the last environment");
    }
    return new CorrectResult<>(environments.pop());
  }

  @Override
  public Result<Environment> pushClosure(FunctionEntry functionEntry) {
    Environment newLocalEnvironment = ENVIRONMENT_FACTORY.createLocalEnvironment(functionEntry.closure());
    environments.push(newLocalEnvironment);
    return new CorrectResult<>(newLocalEnvironment);
  }

  @Override
  public void setExecutionError(IncorrectResult<?> result) {
    this.executionError = result;
  }

  @Override
  public IncorrectResult<?> getExecutionError() {
    return this.executionError;
  }
}
