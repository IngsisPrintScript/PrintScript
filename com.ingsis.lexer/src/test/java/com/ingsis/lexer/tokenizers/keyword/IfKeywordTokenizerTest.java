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
    void tokenize_if_returnsKeyword() {
        Result<Token> result = tokenizer.tokenize("if", 5, 6);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("KEYWORD_TOKEN", t.name());
        assertEquals("if", t.value());
    }

    @Test
    void tokenize_notIf_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("iff", 1, 1);
        assertFalse(result.isCorrect());
    }
}
