/*
 * My Project
 */

package com.ingsis.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CorrectResultTest {

    private CorrectResult<String> correct;

    @BeforeEach
    void setUp() {
        // given: a correct result containing a value
        correct = new CorrectResult<>("ok");
    }

    @Test
    void givenCorrectResult_whenResult_thenReturnsValueAndIsCorrect() {
        // when
        String value = correct.result();

        // then
        assertEquals("ok", value);
        assertTrue(correct.isCorrect());
    }

    @Test
    void givenCorrectResult_whenError_thenThrowsUnsupportedOperation() {
        // then
        assertThrows(UnsupportedOperationException.class, correct::error);
    }
}
