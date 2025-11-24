/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.separator;

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

public class GenericSeparatorTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private GenericSeparatorTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new GenericSeparatorTokenizer(tokenFactory, ",", resultFactory);
    }

    @Test
    void tokenize_comma_returnsSeparator() {
        Result<Token> result = tokenizer.tokenize(",", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("SEPARATOR_TOKEN", t.name());
        assertEquals(",", t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize(";", 1, 1);
        assertFalse(result.isCorrect());
    }
}
