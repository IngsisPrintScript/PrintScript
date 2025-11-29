/*
 * My Project
 */

package com.ingsis.runtime.environment.entries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ingsis.runtime.environment.Environment;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.type.types.Types;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DefaultFunctionEntryTest {

    @Test
    void functionEntryCopiesCollectionsAndExposesFields() {
        DefaultFunctionEntry fe = new DefaultFunctionEntry(Types.NIL, Map.of(), null, null);
        assertEquals(Types.NIL, fe.returnType());
        assertNull(fe.body());

        DefaultFunctionEntry fe2 =
                new DefaultFunctionEntry(
                        Types.NIL,
                        Map.of("a", Types.NUMBER),
                        List.<ExpressionNode>of(),
                        (Environment) null);
        assertEquals(Types.NIL, fe2.returnType());
        assertEquals(1, fe2.arguments().size());
        assertEquals(0, fe2.body().size());
    }
}
