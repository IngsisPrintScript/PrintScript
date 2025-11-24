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

public class EndOfLineSeparatorTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private EndOfLineSeparatorTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new EndOfLineSeparatorTokenizer(tokenFactory, resultFactory);
    }

    @Test
    void tokenize_semicolon_returnsEol() {
        Result<Token> result = tokenizer.tokenize(";", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("END_OF_LINE_TOKEN", t.name());
        assertEquals(";", t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize(",", 1, 1);
        assertFalse(result.isCorrect());
    }
}
