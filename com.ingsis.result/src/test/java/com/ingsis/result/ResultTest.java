package com.ingsis.result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultTest {

    private Result<String> correct;
    private Result<String> incorrect;

    @BeforeEach
    void setUp() {
        // given: a CorrectResult and an IncorrectResult referenced as Result
        correct = new CorrectResult<>("value");
        incorrect = new IncorrectResult<>("err");
    }

    @Test
    void givenResults_whenPolymorphicAccess_thenBehaveAccordingly() {
        // when/then: correct exposes result and is correct
        assertTrue(correct.isCorrect());
        assertEquals("value", correct.result());

        // when/then: incorrect exposes error and is not correct
        assertFalse(incorrect.isCorrect());
        assertEquals("err", incorrect.error());
    }

    @Test
    void resultTypeIsSealed() {
        // Result must be a sealed interface
        assertTrue(Result.class.isSealed());
    }
}
