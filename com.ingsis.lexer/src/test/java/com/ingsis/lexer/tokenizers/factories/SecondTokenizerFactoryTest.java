/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.TestUtils;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SecondTokenizerFactoryTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private SecondTokenizerFactory factory;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        factory = new SecondTokenizerFactory(tokenFactory, resultFactory);
    }

    @Test
    void createTokenizerCanTokenizeKeywordsAndBoolean() {
        var tokenizer = factory.createTokenizer();
        Result<Token> r1 = tokenizer.tokenize("if", 1, 1);
        assertTrue(r1.isCorrect());
        Result<Token> r2 = tokenizer.tokenize("false", 1, 1);
        assertTrue(r2.isCorrect());
    }
}
