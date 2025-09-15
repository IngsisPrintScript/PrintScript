package com.ingsis.printscript.result;

import com.ingsis.printscript.results.CorrectResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestCorrectResult {

    @Test
    public void testCorrectResult() {
        CorrectResult<String> result = new CorrectResult<String>("test");
        Assertions.assertEquals("test", result.result());
        UnsupportedOperationException exception =
               Assertions.assertThrows(UnsupportedOperationException.class, result::errorMessage);
        Assertions.assertEquals("Correct Result does not support errorMessage", exception.getMessage());
        Assertions.assertTrue(result.isSuccessful());
    }
}
