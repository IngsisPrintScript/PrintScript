/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.separator;

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
    void tokenizeSpaceReturnsSpaceToken() {
        Result<Token> result = tokenizer.tokenize(" ", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("SPACE_SEPARATOR_TOKEN", t.name());
        assertEquals(" ", t.value());
    }

    @Test
    void tokenizeOtherReturnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("\n", 1, 1);
        assertFalse(result.isCorrect());
    }
}
