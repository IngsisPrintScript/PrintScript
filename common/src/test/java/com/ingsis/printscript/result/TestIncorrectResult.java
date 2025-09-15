package com.ingsis.printscript.result;

import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIncorrectResult {

    @Test
    public void testCorrectResult() {
        IncorrectResult<String> result = new IncorrectResult<String>("test");
        Assertions.assertEquals("test", result.errorMessage());
        UnsupportedOperationException exception =
                Assertions.assertThrows(UnsupportedOperationException.class, result::result);
        Assertions.assertEquals("Incorrect result does not have a result.", exception.getMessage());
        Assertions.assertFalse(result.isSuccessful());

    }
}
