/*
 * My Project
 */

package com.ingsis.utils.type.typer.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.Test;

class DefaultLiteralTypeGetterTest {

    private final DefaultLiteralTypeGetter getter = new DefaultLiteralTypeGetter();

    @Test
    void literalNumberIsNumber() {
        LiteralNode node = new LiteralNode("123", 1, 1);
        assertEquals(Types.NUMBER, getter.getType(node));
    }
}
