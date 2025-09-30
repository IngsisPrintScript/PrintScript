/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import com.ingsis.runtime.environment.DefaultEnvironment;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.GlobalEnvironment;
import com.ingsis.runtime.environment.entries.factories.EntryFactory;

public final class DefaultEnvironmentFactory implements EnvironmentFactory {
    private final EntryFactory entryFactory;

    public DefaultEnvironmentFactory(EntryFactory entryFactory) {
        this.entryFactory = entryFactory;
    }

    @Override
    public Environment createGlobalEnvironment() {
        return new GlobalEnvironment(entryFactory);
    }

    @Override
    public Environment createLocalEnvironment(Environment father) {
        return new DefaultEnvironment(entryFactory, father);
    }
}
