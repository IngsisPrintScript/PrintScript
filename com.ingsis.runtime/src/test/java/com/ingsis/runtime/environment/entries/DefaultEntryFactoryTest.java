package com.ingsis.runtime.environment.entries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.runtime.environment.Environment;
import com.ingsis.runtime.environment.entries.factories.DefaultEntryFactory;
import com.ingsis.types.Types;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DefaultEntryFactoryTest {

    @Test
    void createVariableAndFunctionEntries() {
        DefaultEntryFactory factory = new DefaultEntryFactory();
        VariableEntry ve = factory.createVariableEntry(Types.STRING, "hi", true);
        assertNotNull(ve);
        assertEquals(Types.STRING, ve.type());

        FunctionEntry fe = factory.createFunctionEntry(Types.NIL, Map.of(), List.<ExpressionNode>of(), null);
        assertNotNull(fe);
        assertEquals(Types.NIL, fe.returnType());
    }
}
