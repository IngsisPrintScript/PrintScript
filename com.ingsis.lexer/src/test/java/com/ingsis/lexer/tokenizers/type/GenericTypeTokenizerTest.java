package com.ingsis.lexer.tokenizers.type;

import com.ingsis.lexer.TestUtils;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void tokenize_matchingType_returnsTypeToken() {
        Result<Token> result = tokenizer.tokenize(Types.NUMBER.keyword(), 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("TYPE_TOKEN", t.name());
        assertEquals(Types.NUMBER.keyword(), t.value());
    }

    @Test
    void tokenize_other_returnsIncorrect() {
        Result<Token> result = tokenizer.tokenize("Unknown", 1, 1);
        assertFalse(result.isCorrect());
    }
}
