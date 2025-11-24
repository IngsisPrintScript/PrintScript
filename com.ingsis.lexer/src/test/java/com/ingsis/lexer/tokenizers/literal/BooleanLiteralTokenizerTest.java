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

public class BooleanLiteralTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private BooleanLiteralTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new BooleanLiteralTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenize_true_returnsLiteral() {
        Result<Token> result = tokenizer.tokenize("true", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("LITERAL_TOKEN", t.name());
        assertEquals("true", t.value());
    }

    @Test
    void tokenize_invalid_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("TRUE", 1, 1);
        assertFalse(result.isCorrect());
    }
}
