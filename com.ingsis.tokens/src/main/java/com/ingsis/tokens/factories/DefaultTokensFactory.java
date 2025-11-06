/*
 * My Project
 */

package com.ingsis.tokens.factories;

import com.ingsis.tokens.DefaultToken;
import com.ingsis.tokens.Token;

public record DefaultTokensFactory() implements TokenFactory {
    @Override
    public Token createKeywordToken(String keyword) {
        return new DefaultToken("KEYWORD_TOKEN", keyword);
    }

    @Override
    public Token createLiteralToken(String literal) {
        return new DefaultToken("LITERAL_TOKEN", literal);
    }

    @Override
    public Token createTypeToken(String type) {
        return new DefaultToken("TYPE_TOKEN", type);
    }

    @Override
    public Token createIdentifierToken(String identifier) {
        return new DefaultToken("IDENTIFIER_TOKEN", identifier);
    }

    @Override
    public Token createSeparatorToken(String separator) {
        return new DefaultToken("SEPARATOR_TOKEN", separator);
    }

    @Override
    public Token createOperatorToken(String operator) {
        return new DefaultToken("OPERATOR_TOKEN", operator);
    }

    @Override
    public Token createEndOfLineToken(String endOfLine) {
        return new DefaultToken("END_OF_LINE_TOKEN", endOfLine);
    }

    @Override
    public Token createSpaceSeparatorToken(String spaceValue) {
        return new DefaultToken("SPACE_SEPARATOR_TOKEN", spaceValue);
    }
}
