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

public class LetKeywordTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private LetKeywordTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new LetKeywordTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenizeLetReturnsKeywordToken() {
        Result<Token> result = tokenizer.tokenize("let", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("KEYWORD_TOKEN", t.name());
        assertEquals("let", t.value());
    }

    @Test
    void tokenizeOtherReturnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("lett", 1, 1);
        assertFalse(result.isCorrect());
    }
}
