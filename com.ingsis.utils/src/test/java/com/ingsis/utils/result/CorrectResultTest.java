/*
 * My Project
 */

package com.ingsis.utils.result;

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
    void givenCorrectResultWhenResultThenReturnsValueAndIsCorrect() {
        // when
        String value = correct.result();

        // then
        assertEquals("ok", value);
        assertTrue(correct.isCorrect());
    }

    @Test
    void givenCorrectResultWhenErrorThenThrowsUnsupportedOperation() {
        // then
        assertThrows(UnsupportedOperationException.class, correct::error);
    }
}
