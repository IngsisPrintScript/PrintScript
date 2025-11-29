/*
 * My Project
 */

package com.ingsis.utils.token.tokens;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenTest {

    private Token token;

    @BeforeEach
    void setUp() {
        token = new DefaultToken("NAME", "value", 10, 20);
    }

    @Test
    void returnsCorrectFields() {
        assertEquals("NAME", token.name());
        assertEquals("value", token.value());
        assertEquals(Integer.valueOf(10), token.line());
        assertEquals(Integer.valueOf(20), token.column());
    }
}
