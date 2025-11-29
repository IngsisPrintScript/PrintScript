/*
 * My Project
 */

package com.ingsis.utils.rule.observer.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CheckerFactoryTest {
    @Test
    void checkerFactoryIsInterface() {
        assertTrue(CheckerFactory.class.isInterface());
    }
}
