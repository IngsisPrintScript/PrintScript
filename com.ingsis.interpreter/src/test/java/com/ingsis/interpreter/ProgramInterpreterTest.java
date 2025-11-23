package com.ingsis.interpreter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ProgramInterpreterTest {
    @Test
    void programInterpreterIsInterface() {
        assertTrue(ProgramInterpreter.class.isInterface());
    }
}
