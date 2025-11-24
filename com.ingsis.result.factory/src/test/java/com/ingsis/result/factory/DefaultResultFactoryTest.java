/*
 * My Project
 */

package com.ingsis.result.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultResultFactoryTest {

    private DefaultResultFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DefaultResultFactory();
    }

    @Test
    void createIncorrectResultProducesIncorrect() {
        IncorrectResult<String> r = factory.createIncorrectResult("err");
        assertEquals("err", r.error());
        assertFalse(r.isCorrect());
        assertThrows(UnsupportedOperationException.class, r::result);
    }

    @Test
    void cloneIncorrectCopiesMessage() {
        Result<?> original = new IncorrectResult<>("orig");
        IncorrectResult<String> cloned = factory.cloneIncorrectResult(original);
        assertEquals("orig", cloned.error());
    }

    @Test
    void createCorrectResultProducesCorrect() {
        CorrectResult<Integer> cr = factory.createCorrectResult(42);
        assertTrue(cr.isCorrect());
        assertEquals(42, cr.result());
        assertThrows(UnsupportedOperationException.class, cr::error);
    }
}
