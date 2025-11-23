package com.ingsis.engine.factories.lexer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LexerFactoryTest {
    @Test
    void isInterface() {
        assertTrue(LexerFactory.class.isInterface());
    }
}
