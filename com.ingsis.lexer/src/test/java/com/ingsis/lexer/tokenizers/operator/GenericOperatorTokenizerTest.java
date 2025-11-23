package com.ingsis.lexer.tokenizers.operator;

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

public class GenericOperatorTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private GenericOperatorTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new GenericOperatorTokenizer(tokenFactory, ":", resultFactory);
    }

    @Test
    void tokenize_colon_returnsOperatorToken() {
        Result<Token> result = tokenizer.tokenize(":", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("OPERATOR_TOKEN", t.name());
        assertEquals(":", t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize(";", 1, 1);
        assertFalse(result.isCorrect());
    }
}
