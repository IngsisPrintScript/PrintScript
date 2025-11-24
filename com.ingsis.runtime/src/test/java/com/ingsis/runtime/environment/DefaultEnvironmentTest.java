/*
 * My Project
 */

package com.ingsis.runtime.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.result.Result;
import com.ingsis.runtime.environment.entries.VariableEntry;
import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.types.Types;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultEnvironmentTest {

    private DefaultEnvironment env;
    private GlobalEnvironment father;

    @BeforeEach
    void setup() {
        DefaultEntryFactory factory = new DefaultEntryFactory();
        father = new GlobalEnvironment(factory);
        env = new DefaultEnvironment(factory, father);
    }

    @Test
    void createReadUpdateAndDeleteVariable() {
        Result<VariableEntry> create = env.createVariable("v", Types.STRING);
        assertTrue(create.isCorrect());

        Result<VariableEntry> read = env.readVariable("v");
        assertTrue(read.isCorrect());

        // update variable
        Result<VariableEntry> update = env.updateVariable("v", "ok");
        assertTrue(update.isCorrect());

        Result<VariableEntry> read2 = env.readVariable("v");
        assertTrue(read2.isCorrect());
        assertEquals("ok", read2.result().value());

        // delete variable should delegate to father when declared here
        Result<VariableEntry> deleted = env.deleteVariable("v");
        // since variable was declared here, DefaultEnvironment delegates to father
        // father.deleteVariable returns IncorrectResult for non-existing identifier
        assertFalse(deleted.isCorrect());
    }

    @Test
    void identifierChecksAndReadAll() {
        env.createVariable("a", Types.NUMBER);
        assertTrue(env.isIdentifierDeclared("a"));
        assertFalse(env.isIdentifierInitialized("a"));

        env.createFunction("f", Map.of(), Types.NIL);
        assertTrue(env.isFunctionDeclared("f"));
    }

    @Test
    void delegationToFatherForReadAndUpdate() {
        // declare variable in father
        father.createVariable("pf", Types.STRING, "pfv");

        // father contains it; child should be able to read it via delegation
        assertTrue(father.isVariableDeclared("pf"));
        assertTrue(env.readVariable("pf").isCorrect());

        // updateVariable on child should delegate to father but fail because father variable
        // was created with an initial value (immutable). Verify it remains unchanged.
        assertFalse(env.updateVariable("pf", "changed").isCorrect());
        assertEquals("pfv", father.readVariable("pf").result().value());
    }
}
