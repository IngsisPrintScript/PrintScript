package common.factories.tokens;

import common.tokens.TokenInterface;

public interface TokenFactoryInterface {
    TokenInterface createLetKeywordToken();
    TokenInterface createPrintlnKeywordToken();
    TokenInterface createIdentifierToken(String identifier);
    TokenInterface createTypeAssignationToken();
    TokenInterface createTypeToken(String type);
    TokenInterface createAssignationToken();
    TokenInterface createLiteralToken(String literal);
    TokenInterface createEndOfLineToken();
}
