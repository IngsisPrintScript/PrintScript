/*
 * My Project
 */

package com.ingsis.runtime.identifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.type.typer.identifier.DefaultIdentifierTypeGetter;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultIdentifierTypeGetterTest {

    private final DefaultRuntime runtime = DefaultRuntime.getInstance();

    @BeforeEach
    void pushEnvironment() {
        runtime.push();
    }

    @AfterEach
    void popEnvironment() {
        runtime.pop();
    }

    @Test
    void unknownIdentifierReturnsUndefined() {
        IdentifierNode node = new IdentifierNode("x", 1, 1);
        DefaultIdentifierTypeGetter getter = new DefaultIdentifierTypeGetter(runtime);
        assertEquals(Types.UNDEFINED, getter.getType(node));
    }

    @Test
    void declaredVariableReturnsItsType() {
        // create variable in current environment
        Environment env = runtime.getCurrentEnvironment();
        env.createVariable("flag", Types.BOOLEAN, true);

        IdentifierNode node = new IdentifierNode("flag", 1, 1);
        DefaultIdentifierTypeGetter getter = new DefaultIdentifierTypeGetter(runtime);
        assertEquals(Types.BOOLEAN, getter.getType(node));
    }
}
