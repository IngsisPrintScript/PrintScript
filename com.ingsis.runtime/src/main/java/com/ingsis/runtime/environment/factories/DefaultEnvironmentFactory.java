/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import com.ingsis.runtime.environment.DefaultEnvironment;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.GlobalEnvironment;

public final class DefaultEnvironmentFactory implements EnvironmentFactory {
    @Override
    public Environment createGlobalEnvironment() {
        return new GlobalEnvironment();
    }

    @Override
    public Environment createLocalEnvironment(Environment father) {
        return new DefaultEnvironment(father);
    }
}
