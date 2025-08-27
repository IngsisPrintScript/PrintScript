package factories.tokens;


import common.TokenInterface;

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
    TokenInterface createEndOfLineToken();
}
