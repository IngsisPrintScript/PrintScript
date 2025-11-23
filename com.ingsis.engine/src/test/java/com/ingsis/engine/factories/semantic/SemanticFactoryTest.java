package com.ingsis.engine.factories.semantic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SemanticFactoryTest {
    @Test
    void isInterface() {
        assertTrue(SemanticFactory.class.isInterface());
    }
}
