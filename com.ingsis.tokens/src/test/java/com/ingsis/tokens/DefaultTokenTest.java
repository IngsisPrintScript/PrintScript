/*
 * My Project
 */

package com.ingsis.tokens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultTokenTest {

    private DefaultToken token;

    @BeforeEach
    void setUp() {
        token = new DefaultToken("NAME", "value", 1, 2);
    }

    @Test
    void constructFromToken_copiesFields() {
        DefaultToken copy = new DefaultToken(token);
        assertEquals(token.name(), copy.name());
        assertEquals(token.value(), copy.value());
        assertEquals(token.line(), copy.line());
        assertEquals(token.column(), copy.column());
    }

    @Test
    void equals_usesNameWhenOtherHasEmptyValue() {
        DefaultToken other = new DefaultToken("NAME", "", 5, 6);
        assertTrue(token.equals(other));
        // other -> token should also be true because other has empty value
        assertTrue(other.equals(token));
    }

    @Test
    void equals_falseForDifferentName() {
        DefaultToken other = new DefaultToken("OTHER", "value", 1, 2);
        assertFalse(token.equals(other));
    }

    @Test
    void equals_falseForNonTokenObject() {
        assertFalse(token.equals("not a token"));
    }
}
