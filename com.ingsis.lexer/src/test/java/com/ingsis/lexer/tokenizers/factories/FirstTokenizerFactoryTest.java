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

public class FirstTokenizerFactoryTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private FirstTokenizerFactory factory;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        factory = new FirstTokenizerFactory(tokenFactory, resultFactory);
    }

    @Test
    void createTokenizerCanTokenizeLetAndOperators() {
        var tokenizer = factory.createTokenizer();
        Result<Token> r1 = tokenizer.tokenize("let", 1, 1);
        assertTrue(r1.isCorrect());
        Result<Token> r2 = tokenizer.tokenize(":", 1, 1);
        assertTrue(r2.isCorrect());
    }
}
