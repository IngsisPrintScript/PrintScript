/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.literal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.TestUtils;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberLiteralTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private NumberLiteralTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new NumberLiteralTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenize_number_returnsLiteral() {
        Result<Token> result = tokenizer.tokenize("12345", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("LITERAL_TOKEN", t.name());
        assertEquals("12345", t.value());
    }

    @Test
    void tokenize_nonNumber_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("12a45", 1, 1);
        assertFalse(result.isCorrect());
    }
}
