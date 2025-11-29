/*
 * My Project
 */

package com.ingsis.utils.type.typer.string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.Test;

class DefaultStringTypeGetterTest {

    private final DefaultStringTypeGetter getter = new DefaultStringTypeGetter();

    @Test
    void numberIsRecognized() {
        assertEquals(Types.NUMBER, getter.getType("123"));
        assertEquals(Types.NUMBER, getter.getType("12.34"));
    }

    @Test
    void booleanIsRecognized() {
        assertEquals(Types.BOOLEAN, getter.getType("true"));
        assertEquals(Types.BOOLEAN, getter.getType("false"));
    }

    @Test
    void nilAndUndefinedAndString() {
        assertEquals(Types.NIL, getter.getType("nil"));
        assertEquals(Types.UNDEFINED, getter.getType("undefined"));
        assertEquals(Types.STRING, getter.getType("abc"));
    }
}
