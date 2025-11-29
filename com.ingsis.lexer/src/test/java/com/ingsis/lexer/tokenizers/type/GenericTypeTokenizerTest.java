/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.TestUtils;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import com.ingsis.utils.type.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenericTypeTokenizerTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private GenericTypeTokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        tokenizer = new GenericTypeTokenizer(tokenFactory, Types.NUMBER, resultFactory);
    }

    @Test
    void tokenizeMatchingTypeReturnsTypeToken() {
        Result<Token> result = tokenizer.tokenize(Types.NUMBER.keyword(), 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("TYPE_TOKEN", t.name());
        assertEquals(Types.NUMBER.keyword(), t.value());
    }

    @Test
    void tokenizeOtherReturnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("Unknown", 1, 1);
        assertFalse(result.isCorrect());
    }
}
