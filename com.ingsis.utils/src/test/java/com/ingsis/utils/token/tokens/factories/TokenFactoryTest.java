/*
 * My Project
 */

package com.ingsis.utils.token.tokens.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ingsis.utils.token.tokens.DefaultToken;
import com.ingsis.utils.token.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenFactoryTest {

    private TokenFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TestTokenFactory();
    }

    private static final class TestTokenFactory implements TokenFactory {
        @Override
        public Token createKeywordToken(String keyword, Integer line, Integer column) {
            return new DefaultToken("KEYWORD_TOKEN", keyword, line, column);
        }

        @Override
        public Token createLiteralToken(String literal, Integer line, Integer column) {
            return new DefaultToken("LITERAL_TOKEN", literal, line, column);
        }

        @Override
        public Token createTypeToken(String type, Integer line, Integer column) {
            return new DefaultToken("TYPE_TOKEN", type, line, column);
        }

        @Override
        public Token createIdentifierToken(String identifier, Integer line, Integer column) {
            return new DefaultToken("IDENTIFIER_TOKEN", identifier, line, column);
        }

        @Override
        public Token createSeparatorToken(String separator, Integer line, Integer column) {
            return new DefaultToken("SEPARATOR_TOKEN", separator, line, column);
        }

        @Override
        public Token createSpaceSeparatorToken(String spaceValue, Integer line, Integer column) {
            return new DefaultToken("SPACE_SEPARATOR_TOKEN", spaceValue, line, column);
        }

        @Override
        public Token createOperatorToken(String operator, Integer line, Integer column) {
            return new DefaultToken("OPERATOR_TOKEN", operator, line, column);
        }

        @Override
        public Token createEndOfLineToken(String endOfLine, Integer line, Integer column) {
            return new DefaultToken("END_OF_LINE_TOKEN", endOfLine, line, column);
        }

        @Override
        public Token createKeywordToken(String keyword) {
            return createKeywordToken(keyword, null, null);
        }

        @Override
        public Token createLiteralToken(String literal) {
            return createLiteralToken(literal, null, null);
        }

        @Override
        public Token createTypeToken(String type) {
            return createTypeToken(type, null, null);
        }

        @Override
        public Token createIdentifierToken(String identifier) {
            return createIdentifierToken(identifier, null, null);
        }

        @Override
        public Token createSeparatorToken(String separator) {
            return createSeparatorToken(separator, null, null);
        }

        @Override
        public Token createSpaceSeparatorToken(String spaceValue) {
            return createSpaceSeparatorToken(spaceValue, null, null);
        }

        @Override
        public Token createOperatorToken(String operator) {
            return createOperatorToken(operator, null, null);
        }

        @Override
        public Token createEndOfLineToken(String endOfLine) {
            return createEndOfLineToken(endOfLine, null, null);
        }
    }

    @Test
    void createKeywordTokenWithoutLineAndColumnHasNulls() {
        Token t = factory.createKeywordToken("if");
        assertEquals("KEYWORD_TOKEN", t.name());
        assertEquals("if", t.value());
        assertNull(t.line());
        assertNull(t.column());
    }

    @Test
    void createLiteralTokenWithLineAndColumnPreservesThem() {
        Token t = factory.createLiteralToken("42", 3, 4);
        assertEquals("LITERAL_TOKEN", t.name());
        assertEquals("42", t.value());
        assertEquals(Integer.valueOf(3), t.line());
        assertEquals(Integer.valueOf(4), t.column());
    }
}
