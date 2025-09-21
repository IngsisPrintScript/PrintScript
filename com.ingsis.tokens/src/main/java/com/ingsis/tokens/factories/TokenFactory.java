/*
 * My Project
 */

package com.ingsis.tokens.factories;

import com.ingsis.tokens.Token;

public interface TokenFactory {
    Token createKeywordToken(String keyword);

    Token createLiteralToken(String literal);

    Token createTypeToken(String type);

    Token createIdentifierToken(String identifier);

    Token createSeparatorToken(String separator);

    Token createPunctuationToken(String punctuationSign);

    Token createOperatorToken(String operator);

    Token createEndOfLineToken(String endOfLine);
}
