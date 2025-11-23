package com.ingsis.lexer.tokenizers;

import com.ingsis.result.Result;
import com.ingsis.result.factory.DefaultResultFactory;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class FinalTokenizerTest {
    @Test
    void tokenize_alwaysReturnsIncorrect() {
        Result<Token> result = new FinalTokenizer().tokenize("any", 1, 1);
        assertFalse(result.isCorrect());
    }
}
