/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import com.ingsis.runtime.environment.Environment;

public interface EnvironmentFactory {
    Environment createGlobalEnvironment();

    Environment createLocalEnvironment(Environment father);
}
