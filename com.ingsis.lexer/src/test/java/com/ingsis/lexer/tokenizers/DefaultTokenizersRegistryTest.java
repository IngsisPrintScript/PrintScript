/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.TestUtils;
import com.ingsis.lexer.tokenizers.identifier.IdentifierTokenizer;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultTokenizersRegistryTest {
    private TokenFactory tokenFactory;
    private ResultFactory resultFactory;
    private DefaultTokenizersRegistry registry;

    @BeforeEach
    void setUp() {
        tokenFactory = TestUtils.tokenFactory();
        resultFactory = TestUtils.resultFactory();
        registry = new DefaultTokenizersRegistry();
    }

    @Test
    void tokenize_registeredMatchesFirst() {
        registry.registerTokenizer(new IdentifierTokenizer(tokenFactory, resultFactory));
        Result<Token> result = registry.tokenize("abc", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("IDENTIFIER_TOKEN", t.name());
    }

    @Test
    void tokenize_noRegistered_returnsFinalTokenizerIncorrect() {
        Result<Token> result = registry.tokenize("@@", 1, 1);
        assertFalse(result.isCorrect());
    }
}
