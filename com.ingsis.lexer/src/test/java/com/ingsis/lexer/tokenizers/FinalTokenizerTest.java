/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import org.junit.jupiter.api.Test;

public class FinalTokenizerTest {
    @Test
    void tokenize_alwaysReturnsIncorrect() {
        Result<Token> result = new FinalTokenizer().tokenize("any", 1, 1);
        assertFalse(result.isCorrect());
    }
}
