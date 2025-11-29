/*
 * My Project
 */

package com.ingsis.utils.token.tokens.factories; /*
                                                  * My Project
                                                  */

import com.ingsis.utils.token.tokens.Token;

public interface TokenFactory {
    Token createKeywordToken(String keyword, Integer line, Integer column);

    Token createLiteralToken(String literal, Integer line, Integer column);

    Token createTypeToken(String type, Integer line, Integer column);

    Token createIdentifierToken(String identifier, Integer line, Integer column);

    Token createSeparatorToken(String separator, Integer line, Integer column);

    Token createSpaceSeparatorToken(String spaceValue, Integer line, Integer column);

    Token createOperatorToken(String operator, Integer line, Integer column);

    Token createEndOfLineToken(String endOfLine, Integer line, Integer column);

    Token createKeywordToken(String keyword);

    Token createLiteralToken(String literal);

    Token createTypeToken(String type);

    Token createIdentifierToken(String identifier);

    Token createSeparatorToken(String separator);

    Token createSpaceSeparatorToken(String spaceValue);

    Token createOperatorToken(String operator);

    Token createEndOfLineToken(String endOfLine);
}
