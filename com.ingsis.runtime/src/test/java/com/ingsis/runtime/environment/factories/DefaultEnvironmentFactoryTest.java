/*
 * My Project
 */

package com.ingsis.runtime.environment.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.types.Types;
import org.junit.jupiter.api.Test;

class DefaultEnvironmentFactoryTest {

    @Test
    void globalEnvironmentHasBuiltins() {
        DefaultEnvironmentFactory factory =
                new DefaultEnvironmentFactory(new DefaultEntryFactory());
        Environment global = factory.createGlobalEnvironment();
        // builtin println must be declared
        assertTrue(global.isFunctionDeclared("println"));
        assertTrue(global.isFunctionDeclared("readInput"));
        assertTrue(global.isFunctionDeclared("readEnv"));

        // create a variable via factory-produced global
        global.createVariable("v", Types.STRING, "hello");
        assertTrue(global.isVariableDeclared("v"));
    }

    @Test
    void builtinsAreInitialized() {
        DefaultEnvironmentFactory factory =
                new DefaultEnvironmentFactory(new DefaultEntryFactory());
        Environment global = factory.createGlobalEnvironment();
        // functions should be initialized (updateFunction called)
        assertTrue(global.isFunctionInitialized("println"));
        assertTrue(global.isFunctionInitialized("readInput"));
        assertTrue(global.isFunctionInitialized("readEnv"));

        // read function and ensure body non-null
        assertTrue(global.readFunction("println").isCorrect());
    }
}
