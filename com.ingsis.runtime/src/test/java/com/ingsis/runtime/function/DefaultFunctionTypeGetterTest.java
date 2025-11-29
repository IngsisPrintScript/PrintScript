/*
 * My Project
 */

package com.ingsis.runtime.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.runtime.DefaultRuntime;
import com.ingsis.runtime.type.typer.function.DefaultFunctionTypeGetter;
import com.ingsis.utils.nodes.nodes.expression.function.CallFunctionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.type.types.Types;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultFunctionTypeGetterTest {

    private final DefaultRuntime runtime = DefaultRuntime.getInstance();

    @BeforeEach
    void push() {
        runtime.push();
    }

    @AfterEach
    void pop() {
        runtime.pop();
    }

    @Test
    void returnsFunctionReturnType() {
        runtime.getCurrentEnvironment().createFunction("f", Map.of(), Types.NUMBER);

        IdentifierNode idNode = new IdentifierNode("f", 1, 1);
        CallFunctionNode call = new CallFunctionNode(idNode, java.util.List.of(), 1, 1);

        DefaultFunctionTypeGetter getter = new DefaultFunctionTypeGetter(runtime);
        assertEquals(Types.NUMBER, getter.getType(call));
    }
}
