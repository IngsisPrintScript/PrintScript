/*
 * My Project
 */

package com.ingsis.typer.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.types.Types;
import org.junit.jupiter.api.Test;

class DefaultLiteralTypeGetterTest {

    private final DefaultLiteralTypeGetter getter = new DefaultLiteralTypeGetter();

    @Test
    void literalNumberIsNumber() {
        LiteralNode node = new LiteralNode("123", 1, 1);
        assertEquals(Types.NUMBER, getter.getType(node));
    }
}
