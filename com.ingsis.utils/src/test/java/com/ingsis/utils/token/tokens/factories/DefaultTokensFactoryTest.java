/*
 * My Project
 */

package com.ingsis.utils.token.tokens.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ingsis.utils.token.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultTokensFactoryTest {

    private DefaultTokensFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DefaultTokensFactory();
    }

    @Test
    void createKeywordTokenWithAndWithoutPosition() {
        Token a = factory.createKeywordToken("if", 1, 2);
        assertEquals("KEYWORD_TOKEN", a.name());
        assertEquals("if", a.value());
        assertEquals(Integer.valueOf(1), a.line());
        assertEquals(Integer.valueOf(2), a.column());

        Token b = factory.createKeywordToken("if");
        assertEquals("KEYWORD_TOKEN", b.name());
        assertEquals("if", b.value());
        assertNull(b.line());
        assertNull(b.column());
    }

    @Test
    void createVariousTokenTypesHaveExpectedNames() {
        Token lit = factory.createLiteralToken("42");
        assertEquals("LITERAL_TOKEN", lit.name());

        Token type = factory.createTypeToken("int");
        assertEquals("TYPE_TOKEN", type.name());

        Token id = factory.createIdentifierToken("x");
        assertEquals("IDENTIFIER_TOKEN", id.name());

        Token sep = factory.createSeparatorToken(",");
        assertEquals("SEPARATOR_TOKEN", sep.name());

        Token op = factory.createOperatorToken("+");
        assertEquals("OPERATOR_TOKEN", op.name());

        Token eol = factory.createEndOfLineToken("\n");
        assertEquals("END_OF_LINE_TOKEN", eol.name());

        Token space = factory.createSpaceSeparatorToken(" ");
        assertEquals("SPACE_SEPARATOR_TOKEN", space.name());
    }
}
