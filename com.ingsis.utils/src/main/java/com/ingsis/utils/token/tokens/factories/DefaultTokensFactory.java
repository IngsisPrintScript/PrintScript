/*
 * My Project
 */

package com.ingsis.utils.token.tokens.factories; /*
                                                  * My Project
                                                  */

import com.ingsis.utils.token.tokens.DefaultToken;
import com.ingsis.utils.token.tokens.Token;

public record DefaultTokensFactory() implements TokenFactory {
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
    public Token createOperatorToken(String operator, Integer line, Integer column) {
        return new DefaultToken("OPERATOR_TOKEN", operator, line, column);
    }

    @Override
    public Token createEndOfLineToken(String endOfLine, Integer line, Integer column) {
        return new DefaultToken("END_OF_LINE_TOKEN", endOfLine, line, column);
    }

    @Override
    public Token createSpaceSeparatorToken(String spaceValue, Integer line, Integer column) {
        return new DefaultToken("SPACE_SEPARATOR_TOKEN", spaceValue, line, column);
    }

    @Override
    public Token createKeywordToken(String keyword) {
        return this.createKeywordToken(keyword, null, null);
    }

    @Override
    public Token createLiteralToken(String literal) {
        return this.createLiteralToken(literal, null, null);
    }

    @Override
    public Token createTypeToken(String type) {
        return this.createTypeToken(type, null, null);
    }

    @Override
    public Token createIdentifierToken(String identifier) {
        return this.createIdentifierToken(identifier, null, null);
    }

    @Override
    public Token createSeparatorToken(String separator) {
        return this.createSeparatorToken(separator, null, null);
    }

    @Override
    public Token createSpaceSeparatorToken(String spaceValue) {
        return this.createSpaceSeparatorToken(spaceValue, null, null);
    }

    @Override
    public Token createOperatorToken(String operator) {
        return this.createOperatorToken(operator, null, null);
    }

    @Override
    public Token createEndOfLineToken(String endOfLine) {
        return this.createEndOfLineToken(endOfLine, null, null);
    }
}
