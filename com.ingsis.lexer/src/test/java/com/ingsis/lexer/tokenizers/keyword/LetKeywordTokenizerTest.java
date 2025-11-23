package com.ingsis.lexer.tokenizers.keyword;

import com.ingsis.lexer.TestUtils;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void tokenize_let_returnsKeywordToken() {
        Result<Token> result = tokenizer.tokenize("let", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("KEYWORD_TOKEN", t.name());
        assertEquals("let", t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("lett", 1, 1);
        assertFalse(result.isCorrect());
    }
}
