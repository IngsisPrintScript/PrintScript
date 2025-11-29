/*
 * My Project
 */

package com.ingsis.runtime.environment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.utils.nodes.nodes.expression.function.GlobalFunctionBody;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import org.junit.jupiter.api.Test;

class DefaultEnvironmentCoverageFinalTest {

    @Test
    void identifierDeclaredAndInitializedChecksAndReadAll() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        GlobalEnvironment father = new GlobalEnvironment(ef);
        DefaultEnvironment child = new DefaultEnvironment(ef, father);

        // neither declared
        assertFalse(child.isIdentifierDeclared("none"));
        assertFalse(child.isIdentifierInitialized("none"));

        // variable declared with initial value
        child.createVariable("vx", Types.STRING, "valx");
        assertTrue(child.isIdentifierDeclared("vx"));
        assertTrue(child.isIdentifierInitialized("vx"));

        // function declared and then initialized
        child.createFunction("fn", java.util.Map.of(), Types.NIL);
        assertTrue(child.isIdentifierDeclared("fn"));
        assertFalse(child.isIdentifierInitialized("fn"));
        child.updateFunction(
                "fn", List.of(new GlobalFunctionBody(List.of(), args -> null, null, null)));
        assertTrue(child.isIdentifierInitialized("fn"));

        // readAll contains variable
        var all = child.readAll();
        assertTrue(all.containsKey("vx"));
    }

    @Test
    void createVariableExistingReturnsIncorrect() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        DefaultEnvironment env = new DefaultEnvironment(ef, new GlobalEnvironment(ef));
        env.createVariable("x", Types.STRING, "v");
        var r = env.createVariable("x", Types.STRING, "v2");
        assertFalse(r.isCorrect());
    }
}
