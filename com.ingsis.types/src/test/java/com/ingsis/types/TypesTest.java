package com.ingsis.types;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypesTest {

    @Test
    void keywordAndRegexAccessors() {
        assertEquals("Number", Types.NUMBER.keyword());
        assertEquals("Boolean", Types.BOOLEAN.keyword());
        assertTrue(Types.STRING.regEx().contains("\\\\")); // regex contains backslash escapes
    }

    @Test
    void checkFormat_numberAndBooleanAndNilAndString() {
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
        assertTrue(Types.STRING.checkFormat("\\\"escaped\\\""));
    }

    @Test
    void isCompatibleWithObject_usesToStringAndFormat() {
        assertTrue(Types.NUMBER.isCompatibleWith(42));
        assertTrue(Types.NUMBER.isCompatibleWith(3.5));
        assertFalse(Types.NUMBER.isCompatibleWith("abc"));

        assertTrue(Types.BOOLEAN.isCompatibleWith(true));
        assertTrue(Types.BOOLEAN.isCompatibleWith("false"));
        assertFalse(Types.BOOLEAN.isCompatibleWith("yes"));
    }

    @Test
    void isCompatibleWithTypes_handlesUndefined() {
        assertTrue(Types.UNDEFINED.isCompatibleWith(Types.NUMBER));
        assertTrue(Types.NUMBER.isCompatibleWith(Types.UNDEFINED));
        assertTrue(Types.STRING.isCompatibleWith(Types.STRING));
        assertFalse(Types.NUMBER.isCompatibleWith(Types.STRING));
    }

    @Test
    void fromKeyword_and_allTypes_behavior() {
        assertEquals(Types.NUMBER, Types.fromKeyword("Number"));
        assertEquals(Types.UNDEFINED, Types.fromKeyword("NonExisting"));

        List<Types> all = Types.allTypes();
        assertEquals(Types.values().length, all.size());
        assertTrue(all.contains(Types.NIL));

        // ensure returned list is unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> all.add(Types.NUMBER));
    }
}
