package com.ingsis.engine.factories.sca;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ScaFactoryTest {
    @Test
    void isInterface() {
        assertTrue(ScaFactory.class.isInterface());
    }
}
