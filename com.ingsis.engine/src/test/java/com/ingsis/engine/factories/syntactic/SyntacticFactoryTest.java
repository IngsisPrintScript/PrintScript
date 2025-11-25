/*
 * My Project
 */

package com.ingsis.engine.factories.syntactic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SyntacticFactoryTest {
    @Test
    void isInterface() {
        assertTrue(SyntacticFactory.class.isInterface());
    }
}
