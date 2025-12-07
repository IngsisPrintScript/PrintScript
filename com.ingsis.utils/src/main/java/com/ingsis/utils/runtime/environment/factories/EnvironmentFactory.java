package com.ingsis.utils.runtime.environment.factories;

import com.ingsis.utils.runtime.environment.Environment;

/*
 * My Project
 */

public interface EnvironmentFactory {
  Environment createGlobalEnvironment();

  Environment createLocalEnvironment(Environment father);
}
