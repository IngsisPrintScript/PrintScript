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

class DefaultEnvironmentAdditionalBranchesTest {

    @Test
    void readFunctionNotDeclaredReturnsIncorrect() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        GlobalEnvironment father = new GlobalEnvironment(ef);
        DefaultEnvironment child = new DefaultEnvironment(ef, father);
        var r = child.readFunction("no");
        assertFalse(r.isCorrect());
    }

    @Test
    void updateFunctionDelegatesToFatherAndLocalUpdateWorks() {
        DefaultEntryFactory ef = new DefaultEntryFactory();
        GlobalEnvironment father = new GlobalEnvironment(ef);
        father.createFunction("fx", java.util.Map.of(), Types.NIL);
        DefaultEnvironment child = new DefaultEnvironment(ef, father);

        // delegate update to father
        var updateRes =
                child.updateFunction(
                        "fx", List.of(new GlobalFunctionBody(List.of(), args -> null, null, null)));
        assertTrue(updateRes.isCorrect());

        // create function locally and update it
        child.createFunction("local", java.util.Map.of(), Types.NIL);
        var up2 =
                child.updateFunction(
                        "local",
                        List.of(new GlobalFunctionBody(List.of(), args -> null, null, null)));
        assertTrue(up2.isCorrect());
        assertTrue(child.isFunctionInitialized("local"));
    }
}
