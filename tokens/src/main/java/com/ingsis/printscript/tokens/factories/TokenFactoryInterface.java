package com.ingsis.printscript.tokens.factories;


import com.ingsis.printscript.tokens.TokenInterface;

public interface TokenFactoryInterface {
    TokenInterface createLetKeywordToken();
    TokenInterface createPrintlnKeywordToken();
    TokenInterface createLeftParenthesisToken();
    TokenInterface createRightParenthesisToken();
    TokenInterface createIdentifierToken(String identifier);
    TokenInterface createTypeAssignationToken();
    TokenInterface createTypeToken(String type);
    TokenInterface createAssignationToken();
    TokenInterface createLiteralToken(String literal);
    TokenInterface createAdditionToken();
    TokenInterface createSeparatorToken(String value);
    TokenInterface createEndOfLineToken();
}
