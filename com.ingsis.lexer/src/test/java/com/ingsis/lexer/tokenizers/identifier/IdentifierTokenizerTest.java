/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.identifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.TestUtils;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IdentifierTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private IdentifierTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new IdentifierTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenizeValidIdentifierReturnsCorrectResult() {
        Result<Token> result = tokenizer.tokenize("abc123", 1, 1);
        assertTrue(result.isCorrect());
        Token token = result.result();
        assertEquals("IDENTIFIER_TOKEN", token.name());
        assertEquals("abc123", token.value());
    }

    @Test
    void tokenizeInvalidIdentifierReturnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("123abc", 2, 3);
        assertFalse(result.isCorrect());
    }
}
