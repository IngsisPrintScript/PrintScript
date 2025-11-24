package com.ingsis.runtime.environment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.types.Types;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GlobalEnvironmentTest {

    private GlobalEnvironment global;

    @BeforeEach
    void setup() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        global = new GlobalEnvironment(ef);
    }

    @Test
    void createAndUpdateVariable() {
        Result<VariableEntry> created = global.createVariable("x", Types.NUMBER);
        // createVariable returns CorrectResult with previous value (null) in this impl
        assertTrue(created.isCorrect());

        Result<VariableEntry> read = global.readVariable("x");
        assertTrue(read.isCorrect());

        Result<VariableEntry> updated = global.updateVariable("x", 10);
        assertTrue(updated.isCorrect());
    }

    @Test
    void createFunctionAndCheckInitialization() {
        global.createFunction("p", Map.of(), Types.NIL);
        // after createFunction, function exists but is not initialized
        assertTrue(global.isFunctionDeclared("p"));
        assertFalse(global.isFunctionInitialized("p"));
    }

    @Test
    void createVariableDuplicateAndImmutableUpdate() {
        // create with separate API that sets immutable
        global.createVariable("y", Types.STRING, "v");
        // now try to update immutable variable -> should be incorrect
        assertFalse(global.updateVariable("y", "new").isCorrect());

        // create variable normally and then creating again must fail
        global.createVariable("z", Types.NUMBER);
        assertFalse(global.createVariable("z", Types.NUMBER).isCorrect());

        // reading a non-existing variable should be incorrect
        assertFalse(global.readVariable("nope").isCorrect());
    }
}
