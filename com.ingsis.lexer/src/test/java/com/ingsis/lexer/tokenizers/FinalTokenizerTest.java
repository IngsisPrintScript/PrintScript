/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import org.junit.jupiter.api.Test;

public class FinalTokenizerTest {
    @Test
    void tokenizeAlwaysReturnsIncorrect() {
        Result<Token> result = new FinalTokenizer().tokenize("any", 1, 1);
        assertFalse(result.isCorrect());
    }
}
