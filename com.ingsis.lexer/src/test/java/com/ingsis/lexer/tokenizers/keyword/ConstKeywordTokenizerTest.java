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

public class ConstKeywordTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private ConstKeywordTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new ConstKeywordTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenize_const_returnsKeyword() {
        Result<Token> result = tokenizer.tokenize("const", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("KEYWORD_TOKEN", t.name());
        assertEquals("const", t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("constant", 1, 1);
        assertFalse(result.isCorrect());
    }
}
