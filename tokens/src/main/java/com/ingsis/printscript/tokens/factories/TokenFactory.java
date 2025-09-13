/*
 * My Project
 */

package com.ingsis.printscript.tokens.factories;

import com.ingsis.printscript.tokens.Token;
import com.ingsis.printscript.tokens.TokenInterface;

public class TokenFactory implements TokenFactoryInterface {
    @Override
    public TokenInterface createLetKeywordToken() {
        return new Token("LET_KEYWORD_TOKEN", "let");
    }

    @Override
    public TokenInterface createPrintlnKeywordToken() {
        return new Token("PRINT_KEYWORD_TOKEN", "println");
    }

    @Override
    public TokenInterface createLeftParenthesisToken() {
        return new Token("LEFT_PARENTHESIS_TOKEN", "(");
    }

    @Override
    public TokenInterface createRightParenthesisToken() {
        return new Token("RIGHT_PARENTHESIS_TOKEN", ")");
    }

    @Override
    public TokenInterface createIdentifierToken(String identifier) {
        return new Token("IDENTIFIER_TOKEN", identifier);
    }

    @Override
    public TokenInterface createTypeAssignationToken() {
        return new Token("TYPE_ASSIGNATION_TOKEN", ":");
    }

    @Override
    public TokenInterface createTypeToken(String type) {
        return new Token("TYPE_TOKEN", type);
    }

    @Override
    public TokenInterface createAssignationToken() {
        return new Token("ASSIGNATION_TOKEN", "=");
    }

    @Override
    public TokenInterface createLiteralToken(String literal) {
        return new Token("LITERAL_TOKEN", literal);
    }

    @Override
    public TokenInterface createAdditionToken() {
        return new Token("ADDITION_TOKEN", "+");
    }

    @Override
    public TokenInterface createSeparatorToken(String value) {
        return new Token("SEPARATOR_TOKEN", value);
    }

    @Override
    public TokenInterface createEndOfLineToken() {
        return new Token("EOL_TOKEN", ";");
    }
}
