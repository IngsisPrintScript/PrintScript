package com.ingsis.rule.observer.factories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckerFactoryTest {
    @Test
    void checkerFactoryIsInterface() {
        assertTrue(CheckerFactory.class.isInterface());
    }
}
