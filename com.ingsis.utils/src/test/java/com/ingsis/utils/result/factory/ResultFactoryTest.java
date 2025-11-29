/*
 * My Project
 */

package com.ingsis.utils.result.factory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultFactoryTest {

    private DefaultResultFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DefaultResultFactory();
    }

    @Test
    void defaultImplementsInterface() {
        assertTrue(factory instanceof ResultFactory);
    }
}
