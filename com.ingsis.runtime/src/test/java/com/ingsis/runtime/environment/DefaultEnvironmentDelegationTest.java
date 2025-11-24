package com.ingsis.runtime.environment;

import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.types.Types;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultEnvironmentDelegationTest {

    @Test
    void readAndUpdateDelegateToFather() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        GlobalEnvironment father = new GlobalEnvironment(ef);
        father.createVariable("v", Types.STRING, "val");

        DefaultEnvironment child = new DefaultEnvironment(ef, father);

        // child should be able to read father's variable
        var readRes = child.readVariable("v");
        assertTrue(readRes.isCorrect());
        assertEquals("val", readRes.result().value());

        // update should delegate to father but fail because father variable was created with
        // an initial value (immutable). Verify father's value stays the same.
        var updateRes = child.updateVariable("v", "newval");
        assertFalse(updateRes.isCorrect());
        var fatherRead = father.readVariable("v");
        assertEquals("val", fatherRead.result().value());
    }

    @Test
    void functionDelegationForReadAndUpdate() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        GlobalEnvironment father = new GlobalEnvironment(ef);
        father.createFunction("g", java.util.Map.of(), Types.NIL);

        DefaultEnvironment child = new DefaultEnvironment(ef, father);

        var read = child.readFunction("g");
        assertTrue(read.isCorrect());

        var update = child.updateFunction("g", List.of());
        assertTrue(update.isCorrect());
    }
}
