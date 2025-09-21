package com.ingsis.tokens.factories;

import com.ingsis.tokens.TokenInterface;

public interface TokenFactory {
    TokenInterface createKeywordToken(String keyword);
    TokenInterface createLiteralToken(String literal);
    TokenInterface createTypeToken(String type);
    TokenInterface createIdentifierToken(String identifier);
    TokenInterface createSeparatorToken(String separator);
    TokenInterface createPunctuationToken(String punctuationSign);
    TokenInterface createOperatorToken(String operator);
    TokenInterface createEndOfLineToken(String endOfLine);
}
