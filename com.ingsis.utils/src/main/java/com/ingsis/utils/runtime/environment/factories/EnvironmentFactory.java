/*
 * My Project
 */

package com.ingsis.utils.runtime.environment.factories;

import com.ingsis.utils.runtime.environment.Environment;

public interface EnvironmentFactory {
    Environment createGlobalEnvironment();

    Environment createLocalEnvironment(Environment father);
}
