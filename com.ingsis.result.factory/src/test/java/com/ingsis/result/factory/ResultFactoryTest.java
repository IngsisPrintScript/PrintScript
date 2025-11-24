package com.ingsis.result.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
