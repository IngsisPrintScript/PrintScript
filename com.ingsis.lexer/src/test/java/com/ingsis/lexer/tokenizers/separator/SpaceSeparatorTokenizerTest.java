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

public class SpaceSeparatorTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private SpaceSeparatorTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new SpaceSeparatorTokenizer(tokenFactory, " ", resultFactory);
    }

    @Test
    void tokenize_space_returnsSpaceToken() {
        Result<Token> result = tokenizer.tokenize(" ", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("SPACE_SEPARATOR_TOKEN", t.name());
        assertEquals(" ", t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("\n", 1, 1);
        assertFalse(result.isCorrect());
    }
}
