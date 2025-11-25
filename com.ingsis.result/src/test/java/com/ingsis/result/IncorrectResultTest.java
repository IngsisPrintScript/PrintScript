/*
 * My Project
 */

package com.ingsis.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncorrectResultTest {

    private IncorrectResult<String> incorrect;

    @BeforeEach
    void setUp() {
        // given: an incorrect result with an error message
        incorrect = new IncorrectResult<>("failure");
    }

    @Test
    void givenIncorrectResultWhenErrorThenReturnsMessageAndIsNotCorrect() {
        // when
        String err = incorrect.error();

        // then
        assertEquals("failure", err);
        assertFalse(incorrect.isCorrect());
    }

    @Test
    void givenIncorrectResultWhenResultThenThrowsUnsupportedOperation() {
        // then
        assertThrows(UnsupportedOperationException.class, incorrect::result);
    }

    @Test
    void givenIncorrectResultWhenConstructedFromResultThenCopiesError() {
        // when: create a new IncorrectResult using existing incorrect
        IncorrectResult<String> copy = new IncorrectResult<>(incorrect);

        // then
        assertEquals("failure", copy.error());
    }
}
