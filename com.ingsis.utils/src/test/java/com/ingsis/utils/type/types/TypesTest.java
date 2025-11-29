/*
 * My Project
 */

package com.ingsis.utils.type.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class TypesTest {

    @Test
    void keywordAndRegexAccessors() {
        assertEquals("number", Types.NUMBER.keyword());
        assertEquals("boolean", Types.BOOLEAN.keyword());
        assertTrue(Types.STRING.regEx().contains("\\")); // regex contains backslash escapes
    }

    @Test
    void checkFormatNumberAndBooleanAndNilAndString() {
        assertTrue(Types.NUMBER.checkFormat("123"));
        assertTrue(Types.NUMBER.checkFormat("3.14"));
        assertFalse(Types.NUMBER.checkFormat("12a"));

        assertTrue(Types.BOOLEAN.checkFormat("true"));
        assertTrue(Types.BOOLEAN.checkFormat("false"));
        assertFalse(Types.BOOLEAN.checkFormat("True"));

        assertTrue(Types.NIL.checkFormat("nil"));
        assertFalse(Types.NIL.checkFormat("null"));

        // STRING regex should accept escaped quotes
        assertTrue(Types.STRING.checkFormat("hello"));
    }

    @Test
    void isCompatibleWithObjectUsesToStringAndFormat() {
        assertTrue(Types.NUMBER.isCompatibleWith(42));
        assertTrue(Types.NUMBER.isCompatibleWith(3.5));
        assertFalse(Types.NUMBER.isCompatibleWith("abc"));

        assertTrue(Types.BOOLEAN.isCompatibleWith(true));
        assertTrue(Types.BOOLEAN.isCompatibleWith("false"));
        assertFalse(Types.BOOLEAN.isCompatibleWith("yes"));
    }

    @Test
    void isCompatibleWithTypesHandlesUndefined() {
        assertTrue(Types.UNDEFINED.isCompatibleWith(Types.NUMBER));
        assertTrue(Types.NUMBER.isCompatibleWith(Types.UNDEFINED));
        assertTrue(Types.STRING.isCompatibleWith(Types.STRING));
        assertFalse(Types.NUMBER.isCompatibleWith(Types.STRING));
    }

    @Test
    void fromKeywordAndAllTypesBehavior() {
        assertEquals(Types.NUMBER, Types.fromKeyword("number"));
        assertEquals(Types.UNDEFINED, Types.fromKeyword("NonExisting"));

        List<Types> all = Types.allTypes();
        assertEquals(Types.values().length, all.size());
        assertTrue(all.contains(Types.NIL));
    }
}
