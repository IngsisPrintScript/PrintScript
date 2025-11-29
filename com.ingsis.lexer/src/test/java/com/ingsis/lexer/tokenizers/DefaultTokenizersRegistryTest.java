/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.lexer.TestUtils;
import com.ingsis.lexer.tokenizers.identifier.IdentifierTokenizer;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.TokenFactory;
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
    void tokenizeRegisteredMatchesFirst() {
        registry.registerTokenizer(new IdentifierTokenizer(tokenFactory, resultFactory));
        Result<Token> result = registry.tokenize("abc", 1, 1);
        assertTrue(result.isCorrect());
        Token t = result.result();
        assertEquals("IDENTIFIER_TOKEN", t.name());
    }

    @Test
    void tokenizeNoRegisteredReturnsFinalTokenizerIncorrect() {
        Result<Token> result = registry.tokenize("@@", 1, 1);
        assertFalse(result.isCorrect());
    }
}
