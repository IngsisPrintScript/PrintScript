/*
 * My Project
 */

package com.ingsis.engine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EngineTest {
    @Test
    void engineExtendsRunnable() {
        assertTrue(Runnable.class.isAssignableFrom(Engine.class));
    }
}
