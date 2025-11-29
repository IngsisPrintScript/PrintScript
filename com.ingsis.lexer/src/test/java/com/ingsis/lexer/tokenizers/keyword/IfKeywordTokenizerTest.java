/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.keyword;

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

public class IfKeywordTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private IfKeywordTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new IfKeywordTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenizeIfReturnsKeyword() {
        Result<Token> result = tokenizer.tokenize("if", 5, 6);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("KEYWORD_TOKEN", t.name());
        assertEquals("if", t.value());
    }

    @Test
    void tokenizeNotIfReturnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("iff", 1, 1);
        assertFalse(result.isCorrect());
    }
}
