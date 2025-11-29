/*
 * My Project
 */

package com.ingsis.runtime.environment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GlobalEnvironmentMoreTest {

    @Test
    void createVariableAlreadyDeclaredReturnsIncorrect() {
        GlobalEnvironment env = new GlobalEnvironment(new DefaultEntryFactory());
        env.createVariable("x", Types.STRING);
        var result = env.createVariable("x", Types.STRING);
        assertFalse(result.isCorrect());
    }

    @Test
    void updateVariableNotDeclaredReturnsIncorrect() {
        GlobalEnvironment env = new GlobalEnvironment(new DefaultEntryFactory());
        var result = env.updateVariable("x", "v");
        assertFalse(result.isCorrect());
    }

    @Test
    void updateImmutableVariableReturnsIncorrect() {
        GlobalEnvironment env = new GlobalEnvironment(new DefaultEntryFactory());
        env.createVariable("x", Types.STRING); // created mutable by default
        // create immutable variant via createVariable(identifier, type, value)
        env.createVariable("y", Types.STRING, "val");
        var r = env.updateVariable("y", "new");
        assertFalse(r.isCorrect());
    }

    @Test
    void isVariableInitializedWorks() {
        GlobalEnvironment env = new GlobalEnvironment(new DefaultEntryFactory());
        env.createVariable("a", Types.STRING);
        assertFalse(env.isVariableInitialized("a"));
        env.createVariable("b", Types.STRING, "v");
        assertTrue(env.isVariableInitialized("b"));
    }

    @Test
    void createReadUpdateFunctionPaths() {
        GlobalEnvironment env = new GlobalEnvironment(new DefaultEntryFactory());
        var create = env.createFunction("f", Map.of(), Types.NIL);
        assertTrue(create.isCorrect());
        var read = env.readFunction("f");
        assertTrue(read.isCorrect());
        var update = env.updateFunction("f", List.of());
        assertTrue(update.isCorrect());
    }
}
